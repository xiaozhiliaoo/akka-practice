package demo5;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.cluster.pubsub.DistributedPubSub;
import akka.cluster.pubsub.DistributedPubSubMediator;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author lili
 * @date 2022/4/10 21:15
 */
public class Publisher extends AbstractActor {

    // activate the extension
    ActorRef mediator = DistributedPubSub.get(getContext().system()).mediator();

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(
                        String.class,
                        in -> {
                            System.out.println("publish message:" + in);
                            String out = in.toUpperCase();
                            mediator.tell(new DistributedPubSubMediator.Publish("content", out), getSelf());
                        })
                .build();
    }

    public static void main(String[] args) {
        String port = "2554";

        Map<String, Object> overrides = new HashMap<>();
        overrides.put("akka.remote.artery.canonical.port", port);
        overrides.put("akka.cluster.roles", Collections.singletonList("pub"));

        Config config = ConfigFactory.parseMap(overrides)
                .withFallback(ConfigFactory.load("PubSub.conf"));

        ActorSystem system = ActorSystem.create("ClusterSystem", config);


        ActorRef publisher = system.actorOf(Props.create(Publisher.class), "publisher");
        // after a while the subscriptions are replicated
        publisher.tell("hello", null);


    }
}