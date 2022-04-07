package org.lili.akka.classic.actors;

import akka.actor.Actor;
import akka.actor.IndirectActorProducer;

/**
 * @author lili
 * @date 2022/3/28 17:12
 */
public class DependencyInjector implements IndirectActorProducer {
    final Object applicationContext;
    final String beanName;

    public DependencyInjector(Object applicationContext, String beanName) {
        this.applicationContext = applicationContext;
        this.beanName = beanName;
    }

    @Override
    public Class<? extends Actor> actorClass() {
        return TheActor.class;
    }

    @Override
    public TheActor produce() {
        TheActor result;
        result = new TheActor((String) applicationContext);
        return result;
    }

    public static void main(String[] args) {

    }
}
