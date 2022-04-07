package org.lili.akka.classic.actors;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;

/**
 * @author lili
 * @date 2022/3/28 17:08
 */
public class FirstActor extends AbstractActor {

    final ActorRef child = getContext().actorOf(Props.create(MyActor.class), "myChild");

    @Override
    public Receive createReceive() {
        return receiveBuilder().matchAny(x -> getSender().tell(x, getSelf())).build();
    }

}
