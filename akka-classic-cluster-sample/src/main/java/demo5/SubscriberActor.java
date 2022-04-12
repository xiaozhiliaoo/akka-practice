package demo5;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.cluster.pubsub.DistributedPubSub;
import akka.cluster.pubsub.DistributedPubSubMediator;
import akka.event.Logging;
import akka.event.LoggingAdapter;

/**
 * @author lili
 * @date 2022/4/10 21:15
 */
public class SubscriberActor extends AbstractActor {
    LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    public SubscriberActor() {
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
