/*
 * Copyright (C) 2009-2022 Lightbend Inc. <https://www.lightbend.com>
 */

package akka.cluster.pubsub;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.cluster.Cluster;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


public class DistributedPubSubMediatorMain {

    public static void main(String[] args) {
        String port = args[0];
        String role = args[1];
        Objects.requireNonNull(port, "port must has 2551 2552 (seedNodes)");
        Objects.requireNonNull(role, "role must be pub or sub");
        Map<String, Object> overrides = new HashMap<>();
        overrides.put("akka.remote.artery.canonical.port", "port");
        overrides.put("akka.cluster.roles", Collections.singletonList(role));

        Config config = ConfigFactory.parseMap(overrides)
                .withFallback(ConfigFactory.load("PubSub.conf"));

        ActorSystem system = ActorSystem.create("ClusterSystem", config);

        Cluster cluster = Cluster.get(system);
        if (cluster.selfMember().hasRole("pub")) {
            // #publish-message
            // somewhere else
            ActorRef publisher = system.actorOf(Props.create(Publisher.class), "publisher");
            // after a while the subscriptions are replicated
            publisher.tell("hello", null);
            // #publish-message
        }
        if (cluster.selfMember().hasRole("sub")) {
            // #start-subscribers
            system.actorOf(Props.create(Subscriber.class), "subscriber1:" + port);
            // another node
            system.actorOf(Props.create(Subscriber.class), "subscriber2:" + port);
            system.actorOf(Props.create(Subscriber.class), "subscriber3:" + port);
            // #start-subscribers
        }


    }


    public // #subscriber
    static class Subscriber extends AbstractActor {
        LoggingAdapter log = Logging.getLogger(getContext().system(), this);

        public Subscriber() {
            ActorRef mediator = DistributedPubSub.get(getContext().system()).mediator();
            // subscribe to the topic named "content"
            mediator.tell(new DistributedPubSubMediator.Subscribe("content", getSelf()), getSelf());
        }

        @Override
        public Receive createReceive() {
            return receiveBuilder()
                    .match(String.class, msg -> log.info("Got: {}", msg))
                    .match(DistributedPubSubMediator.SubscribeAck.class, msg -> log.info("subscribed"))
                    .build();
        }
    }

    // #subscriber

    public // #publisher
    static class Publisher extends AbstractActor {

        // activate the extension
        ActorRef mediator = DistributedPubSub.get(getContext().system()).mediator();

        @Override
        public Receive createReceive() {
            return receiveBuilder()
                    .match(
                            String.class,
                            in -> {
                                String out = in.toUpperCase();
                                mediator.tell(new DistributedPubSubMediator.Publish("content", out), getSelf());
                            })
                    .build();
        }
    }
    // #publisher

    public // #send-destination
    static class Destination extends AbstractActor {
        LoggingAdapter log = Logging.getLogger(getContext().system(), this);

        public Destination() {
            ActorRef mediator = DistributedPubSub.get(getContext().system()).mediator();
            // register to the path
            mediator.tell(new DistributedPubSubMediator.Put(getSelf()), getSelf());
        }

        @Override
        public Receive createReceive() {
            return receiveBuilder().match(String.class, msg -> log.info("Got: {}", msg)).build();
        }
    }

    // #send-destination

    public // #sender
    static class Sender extends AbstractActor {

        // activate the extension
        ActorRef mediator = DistributedPubSub.get(getContext().system()).mediator();

        @Override
        public Receive createReceive() {
            return receiveBuilder()
                    .match(
                            String.class,
                            in -> {
                                String out = in.toUpperCase();
                                boolean localAffinity = true;
                                mediator.tell(
                                        new DistributedPubSubMediator.Send("/user/destination", out, localAffinity),
                                        getSelf());
                            })
                    .build();
        }
    }
    // #sender
}
