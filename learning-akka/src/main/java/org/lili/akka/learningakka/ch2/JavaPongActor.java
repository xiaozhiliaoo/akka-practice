package org.lili.akka.learningakka.ch2;

import akka.actor.*;

/**
 * @author lili
 * @date 2021/5/25 0:04
 */
public class JavaPongActor extends AbstractActor {
    @Override
    public Receive createReceive() {

        //sender() 获取发送者的ActorRef. 给发送者返回消息
        return receiveBuilder().
                matchEquals("Ping", s ->
                        sender().tell("Pong", ActorRef.noSender())).
                matchAny(x ->
                        sender().tell(
                                new Status.Failure(new Exception("unknown message")), self()
                        )).
                build();
    }

    public static void main(String[] args) {
        ActorSystem system = ActorSystem.create("pongActorSystem");
        ActorRef actor = system.actorOf(Props.create(JavaPongActor.class), "pongActor");

    }
}