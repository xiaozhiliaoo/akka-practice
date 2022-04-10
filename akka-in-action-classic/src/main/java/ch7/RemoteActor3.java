package ch7;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.routing.FromConfig;
import com.typesafe.config.ConfigFactory;

public class RemoteActor3 extends AbstractActor {

    @Override
    public Receive createReceive() {
        return receiveBuilder().matchAny(message -> {
            System.out.println("RemoteActor3: " + message);
        }).build();
    }

    public static void main(String[] args) {
        ActorSystem system = ActorSystem.create("sys", ConfigFactory.load("remote3.conf"));
        ActorRef router = system.actorOf(FromConfig.getInstance().props(), "rmtCommon");
        router.tell("hello rmt", ActorRef.noSender());
    }
}
