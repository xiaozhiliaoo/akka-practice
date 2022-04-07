package org.lili.akka.classic.actors;

import akka.actor.AbstractActorWithTimers;

import java.time.Duration;

/**
 * @author lili
 * @date 2022/3/28 18:14
 */
public class ScheduledActor extends AbstractActorWithTimers {

    private static Object TICK_KEY = "TickKey";

    private static final class FirstTick {
    }

    private static final class Tick {
    }

    public ScheduledActor() {
        getTimers().startSingleTimer(TICK_KEY, new FirstTick(), Duration.ofMillis(500));
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(
                        FirstTick.class,
                        message -> {
                            // do something useful here
                            getTimers().startPeriodicTimer(TICK_KEY, new Tick(), Duration.ofSeconds(1));
                        })
                .match(
                        Tick.class,
                        message -> {
                            // do something useful here
                        })
                .build();
    }
}