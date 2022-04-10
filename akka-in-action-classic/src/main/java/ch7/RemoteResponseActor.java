package ch7;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

/**
 * @author lili
 * @date 2022/4/10 17:20
 */
public class RemoteResponseActor extends AbstractActor {
    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .matchAny(msg -> {
                    System.out.println("receive msg:" + msg);
                })
                .build();
    }

    public static void main(String[] args) {
        Config config = ConfigFactory.parseString("akka.remote.classic.netty.tcp.port=" + 2552).
                withFallback(ConfigFactory.load("remote.conf"));
        ActorSystem system = ActorSystem.create("remoteDemo", config);
        ActorRef actorRef = system.actorOf(Props.create(RemoteResponseActor.class), "response");
        System.out.println(actorRef);
    }
}
