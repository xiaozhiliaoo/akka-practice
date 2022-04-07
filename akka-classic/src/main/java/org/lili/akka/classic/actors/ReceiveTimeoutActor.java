package org.lili.akka.classic.actors;

import akka.actor.AbstractActor;
import akka.actor.ReceiveTimeout;

import java.time.Duration;

/**
 * @author lili
 * @date 2022/3/28 18:12
 */
public class ReceiveTimeoutActor extends AbstractActor {
    public ReceiveTimeoutActor() {
        // To set an initial delay
        getContext().setReceiveTimeout(Duration.ofSeconds(10));
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .matchEquals(
                        "Hello",
                        s -> {
                            // To set in a response to a message
                            getContext().setReceiveTimeout(Duration.ofSeconds(1));
                        })
                .match(
                        ReceiveTimeout.class,
                        r -> {
                            // To turn it off
                            getContext().cancelReceiveTimeout();
                        })
                .build();
    }
}
