package org.lili.akka.classic.actors;

import akka.actor.AbstractActor;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;

/**
 * @author lili
 * @date 2022/3/28 16:33
 */
public class MyActor extends AbstractActor {

    private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(
                        String.class,
                        s -> {
                            log.info("Received String message: {}", s);
                        })
                .matchAny(o -> log.info("received unknown message"))
                .build();
    }

    public static void main(String[] args) {
        Props props1 = Props.create(MyActor.class);
        System.out.println(props1.dispatcher());
    }
}
