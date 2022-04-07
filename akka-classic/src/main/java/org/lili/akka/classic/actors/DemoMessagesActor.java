package org.lili.akka.classic.actors;

import akka.actor.AbstractLoggingActor;

/**
 * @author lili
 * @date 2022/3/28 17:07
 */
public class DemoMessagesActor extends AbstractLoggingActor {

    public static class Greeting {
        private final String from;

        public Greeting(String from) {
            this.from = from;
        }

        public String getGreeter() {
            return from;
        }
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(
                        Greeting.class,
                        g -> {
                            log().info("I was greeted by {}", g.getGreeter());
                        })
                .build();
    }
}

