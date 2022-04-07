package org.lili.akka.classic.actors;

import akka.actor.AbstractActor;

/**
 * @author lili
 * @date 2022/3/28 17:28
 */
public class TheActor extends AbstractActor {

    private final Object applicationContext;

    public TheActor(Object applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public Receive createReceive() {
        return null;
    }
}
