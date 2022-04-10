package org.lili.akka.classic.actors;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

import java.util.Optional;

/**
 * @author lili
 * @date 2022/4/10 12:06
 */
public class ActorLifeCycle extends AbstractActor {

    @Override
    public void preStart() throws Exception {
        System.out.println("preStart ...");
        super.preStart();
    }

    @Override
    public void postStop() throws Exception {
        System.out.println("postStop ...");
        super.postStop();
    }

    @Override
    public void preRestart(Throwable reason, Optional<Object> message) throws Exception {
        System.out.println("preRestart ...");
        super.preRestart(reason, message);
    }

    @Override
    public void postRestart(Throwable reason) throws Exception {
        System.out.println("postRestart ...");
        super.postRestart(reason);
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(
                        Integer.class,
                        i -> {
                            getSender().tell(i, getSelf());
                        })
                .build();
    }

    public static void main(String[] args) throws Exception {
        ActorSystem system = ActorSystem.create("lifecycle");
        ActorRef actorRef = system.actorOf(Props.create(ActorLifeCycle.class));
        Thread.sleep(5000);
        system.stop(actorRef);
    }
}
