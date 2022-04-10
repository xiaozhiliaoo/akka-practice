package ch5;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.routing.RoundRobinPool;

//routee
class TaskActor extends AbstractActor {

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .matchAny(message -> {
                    System.out.println(getSelf() + "-->" + message + "-->" + getContext().parent());
                }).build();
    }

}

public class MasterRouterActor extends AbstractActor {
    ActorRef router = null;

    @Override
    public void preStart() throws Exception {
        router = getContext().actorOf(new RoundRobinPool(3).props(Props.create(TaskActor.class)), "taskActor");
//        router = getContext().actorOf(FromConfig.getInstance().withSupervisorStrategy(strategy).props(Props.create(TaskActor.class)), "taskActor");

        System.out.println("router:" + router);
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder().matchAny(message -> {
            router.tell(message, getSender());
        }).build();
    }

    public static void main(String[] args) {
        ActorSystem system = ActorSystem.create("sys");
        ActorRef ref = system.actorOf(Props.create(MasterRouterActor.class), "masterRouterActor");
        ref.tell("HelloA", ActorRef.noSender());
        ref.tell("HelloB", ActorRef.noSender());
        ref.tell("HelloC", ActorRef.noSender());
    }
}
