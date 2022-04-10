package akka.cluster.pubsub;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author lili
 * @date 2022/4/10 21:15
 */
public class Subscriber extends AbstractActor {
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

    public static void main(String[] args) {

        String port = "2551";
        Map<String, Object> overrides = new HashMap<>();
        overrides.put("akka.remote.artery.canonical.port", port);
        overrides.put("akka.cluster.roles", Collections.singletonList("sub"));

        Config config = ConfigFactory.parseMap(overrides)
                .withFallback(ConfigFactory.load("PubSub.conf"));

        ActorSystem system = ActorSystem.create("ClusterSystem", config);


        // #start-subscribers
        system.actorOf(Props.create(Subscriber.class), "subscriber:" + port);
        // #start-subscribers

    }
}
