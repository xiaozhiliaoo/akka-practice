package ch6.spring;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("actorDemo")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ActorDemo extends AbstractActor {

    @Autowired
    private MyService service;


    @Override
    public Receive createReceive() {
        return receiveBuilder().matchAny(msg -> service.saveMsg(msg)).build();
    }

    public static void main(String[] args) {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        ctx.scan("ch6.spring");
        ctx.refresh();
        ActorSystem system = ctx.getBean(ActorSystem.class);
        ActorRef ref = system.actorOf(SpringExtProvider.getInstance().get(system).createProps("actorDemo"), "actorDemo");
        ref.tell("hello", ActorRef.noSender());
    }
}
