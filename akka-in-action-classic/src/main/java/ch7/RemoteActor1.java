package ch7;

import akka.actor.AbstractActor;
import akka.actor.ActorSystem;
import akka.actor.Props;
import com.typesafe.config.ConfigFactory;

public class RemoteActor1 extends AbstractActor {

    @Override
    public Receive createReceive() {
        return receiveBuilder().matchAny(message -> {
            System.out.println("RemoteActor1: " + message);

        }).build();
    }

    public static void main(String[] args) {
        ActorSystem system = ActorSystem.create("sys", ConfigFactory.load("remote1.conf"));
        system.actorOf(Props.create(RemoteActor1.class), "rmt1");
    }
}
