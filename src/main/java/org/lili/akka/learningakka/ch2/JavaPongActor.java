package org.lili.akka.learningakka.ch2;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Status;

/**
 * @author lili
 * @date 2021/5/25 0:04
 */
public class JavaPongActor extends AbstractActor {
    @Override
    public Receive createReceive() {

        return receiveBuilder().
                matchEquals("Ping", s ->
                        sender().tell("Pong", ActorRef.noSender())).
                matchAny(x ->
                        sender().tell(
                                new Status.Failure(new Exception("unknown message")), self()
                        )).
                build();
    }
}