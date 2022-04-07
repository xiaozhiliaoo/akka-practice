package org.lili.akka.classic.actors;

import akka.actor.AbstractActor;
import akka.actor.Props;

/**
 * @author lili
 * @date 2022/3/28 16:58
 */
public class DemoActor extends AbstractActor {
    static Props props(Integer magicNumber) {
        // You need to specify the actual type of the returned actor
        // since Java 8 lambdas have some runtime type information erased
        return Props.create(DemoActor.class, () -> new DemoActor(magicNumber));
    }

    private final Integer magicNumber;

    public DemoActor(Integer magicNumber) {
        this.magicNumber = magicNumber;
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(
                        Integer.class,
                        i -> {
                            getSender().tell(i + magicNumber, getSelf());
                        })
                .build();
    }
}
