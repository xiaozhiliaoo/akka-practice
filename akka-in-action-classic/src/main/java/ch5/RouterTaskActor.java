package ch5;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.routing.*;
import scala.concurrent.duration.Duration;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

class RouteeActor extends AbstractActor {

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .matchAny(msg -> {
                    System.out.println(getSelf() + "--->" + msg);
                })
                .build();
    }
}

public class RouterTaskActor extends AbstractActor {

    private Router router;

    @Override
    public void preStart() throws Exception {
        List<Routee> listRoutee = new ArrayList<Routee>();
        for (int i = 0; i < 2; i++) {
            ActorRef ref = getContext().actorOf(Props.create(RouteeActor.class), "routeeActor" + i);
            listRoutee.add(new ActorRefRoutee(ref));
        }
//        router = new Router(new RoundRobinRoutingLogic(), listRoutee);
//        router = new Router(new BroadcastRoutingLogic(), listRoutee);
//        router = new Router(new ScatterGatherFirstCompletedRoutingLogic(Duration.create(1, TimeUnit.MINUTES)), listRoutee);
        router = new Router(new RandomRoutingLogic(), listRoutee);
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder().matchAny(msg -> {
            router.route(msg, getSender());

        }).build();
    }

    public static void main(String[] args) {
        ActorSystem system = ActorSystem.create("sys");
        ActorRef ref = system.actorOf(Props.create(RouterTaskActor.class), "routerTaskActor");
        ref.tell("HelloA", ActorRef.noSender());
        ref.tell("HelloB", ActorRef.noSender());
        ref.tell("HelloC", ActorRef.noSender());
    }
}
