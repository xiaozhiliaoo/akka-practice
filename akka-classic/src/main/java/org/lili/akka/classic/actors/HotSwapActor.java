package org.lili.akka.classic.actors;

import akka.actor.AbstractActor;

/**
 * @author lili
 * @date 2022/3/28 18:25
 */
public class HotSwapActor extends AbstractActor {
    private AbstractActor.Receive angry;
    private AbstractActor.Receive happy;

    public HotSwapActor() {
        angry = receiveBuilder()
                        .matchEquals(
                                "foo",
                                s -> {
                                    getSender().tell("I am already angry?", getSelf());
                                })
                        .matchEquals(
                                "bar",
                                s -> {
                                    getContext().become(happy);
                                })
                        .build();

        happy = receiveBuilder()
                        .matchEquals(
                                "bar",
                                s -> {
                                    getSender().tell("I am already happy :-)", getSelf());
                                })
                        .matchEquals(
                                "foo",
                                s -> {
                                    getContext().become(angry);
                                })
                        .build();
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .matchEquals("foo", s -> getContext().become(angry))
                .matchEquals("bar", s -> getContext().become(happy))
                .build();
    }
}
