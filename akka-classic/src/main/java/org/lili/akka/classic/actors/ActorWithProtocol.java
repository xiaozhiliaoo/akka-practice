package org.lili.akka.classic.actors;

import akka.actor.AbstractActorWithStash;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

/**
 * @author lili
 * @date 2022/3/28 20:55
 */
public class ActorWithProtocol extends AbstractActorWithStash {

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .matchEquals(
                        "open",
                        s -> {
                            getContext().become(
                                    receiveBuilder()
                                            .matchEquals(
                                                    "write",
                                                    ws -> {
                                                        /* do writing */
                                                    })
                                            .matchEquals(
                                                    "close",
                                                    cs -> {
                                                        unstashAll();
                                                        getContext().unbecome();
                                                    })
                                            .matchAny(msg -> stash())
                                            .build(),
                                    false);
                        })
                .matchAny(msg -> stash())
                .build();
    }


    public static void main(String[] args) throws Exception {
        ActorSystem system = ActorSystem.create("ActorWithProtocolSystem");
        ActorRef actorWithProtocol = system.actorOf(Props.create(ActorWithProtocol.class), "ActorWithProtocol");
        actorWithProtocol.tell("open", ActorRef.noSender());
        actorWithProtocol.tell("write", ActorRef.noSender());
        actorWithProtocol.tell("write", ActorRef.noSender());
        actorWithProtocol.tell("write", ActorRef.noSender());
        actorWithProtocol.tell("open", ActorRef.noSender());
        actorWithProtocol.tell("open", ActorRef.noSender());
        system.terminate();
    }
}
