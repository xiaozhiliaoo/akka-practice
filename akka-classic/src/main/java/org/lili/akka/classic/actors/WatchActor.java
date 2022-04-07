package org.lili.akka.classic.actors;

import akka.actor.*;

/**
 * @author lili
 * @date 2022/3/28 17:29
 */
public class WatchActor extends AbstractActor {

    static ActorSystem system = ActorSystem.create("ActorDocTest");

    private final ActorRef child = getContext().actorOf(Props.empty(), "target");
    private ActorRef lastSender = system.deadLetters();

    public WatchActor() {
        getContext().watch(child); // <-- this is the only call needed for registration
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .matchEquals(
                        "kill",
                        s -> {
                            getContext().stop(child);
                            lastSender = getSender();
                        })
                .match(
                        Terminated.class,
                        t -> t.actor().equals(child),
                        t -> {
                            lastSender.tell("finished", getSelf());
                        })
                .build();
    }
}
