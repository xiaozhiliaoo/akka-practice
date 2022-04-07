package org.lili.akka.classic.actors;

import akka.actor.AbstractLoggingActor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

import static org.lili.akka.classic.actors.Messages.Swap.Swap;

/**
 * @author lili
 * @date 2022/3/28 20:42
 */
public class Swapper extends AbstractLoggingActor {
    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .matchEquals(
                        Swap,
                        s -> {
                            log().info("Hi");
                            Receive ho = receiveBuilder()
                                    .matchEquals(
                                            Swap,
                                            x -> {
                                                log().info("Ho");
                                                getContext().unbecome(); // resets the latest 'become' (just for fun)
                                            })
                                    .build();
                            getContext().become(ho, false); // push on top instead of replace
                        })
                .build();
    }

    public static void main(String[] args) {
        ActorSystem system = ActorSystem.create("SwapperSystem");
        ActorRef swapper = system.actorOf(Props.create(Swapper.class), "swapper");
        swapper.tell(Swap, ActorRef.noSender()); // logs Hi
        swapper.tell(Swap, ActorRef.noSender()); // logs Ho
        swapper.tell(Swap, ActorRef.noSender()); // logs Hi
        swapper.tell(Swap, ActorRef.noSender()); // logs Ho
        swapper.tell(Swap, ActorRef.noSender()); // logs Hi
        swapper.tell(Swap, ActorRef.noSender()); // logs Ho
        system.terminate();
    }
}