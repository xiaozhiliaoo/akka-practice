package ch7;

import akka.actor.*;
import com.typesafe.config.ConfigFactory;

import java.util.Optional;

public class RemoteClientActor extends AbstractActor {


    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .matchEquals(String.class, message -> {
                    if ("talk".equals(message)) {
                        ActorSelection selection = getContext().actorSelection("akka.tcp://sys@127.0.0.1:2552/user/remoteActor");
                        selection.tell(new Identify("666"), getSelf());
                    } else {
                        System.out.println(message);
                    }
                })
                .match(ActorIdentity.class, message -> {
                    ActorIdentity ai = (ActorIdentity) message;
                    Optional<ActorRef> actorRef = ai.getActorRef();
                    actorRef.ifPresent(ref -> ref.tell("Hello remoteActor", getSelf()));
                })
                .build();
    }


    public static void main(String[] args) {
        ActorSystem system = ActorSystem.create("sysCli", ConfigFactory.load("remote-cli.conf"));
        ActorRef actorRef = system.actorOf(Props.create(RemoteClientActor.class), "cliActor");
        actorRef.tell("talk", ActorRef.noSender());
    }
}
