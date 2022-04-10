package ch6.spring;

import akka.actor.Actor;
import akka.actor.IndirectActorProducer;
import org.springframework.context.ApplicationContext;

public class SpringDI implements IndirectActorProducer {

    public SpringDI(ApplicationContext applicationContext, String beanName) {
        this.applicationContext = applicationContext;
        this.beanName = beanName;
    }

    private ApplicationContext applicationContext;

    private String beanName;

    @Override
    public Class<? extends Actor> actorClass() {
        return (Class<? extends Actor>) applicationContext.getType(beanName);
    }

    @Override
    public Actor produce() {
        return (Actor) applicationContext.getBean(beanName);
    }
}
