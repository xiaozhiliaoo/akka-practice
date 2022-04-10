package ch7;

import akka.actor.*;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

/**
 * @author lili
 * @date 2022/4/10 17:08
 */
public class RemoteRequestActor extends AbstractActor {
    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .build();
    }

    public static void main(String[] args) {
        Config config = ConfigFactory.parseString("akka.remote.classic.netty.tcp.port=" + 2551).
                withFallback(ConfigFactory.load("remote.conf"));
        ActorSystem system = ActorSystem.create("remoteDemo", config);
        ActorRef actorRef = system.actorOf(Props.create(RemoteRequestActor.class));
        System.out.println(actorRef);
        ActorSelection selection = system.actorSelection("akka.tcp://remoteDemo@127.0.0.1:2552/user/response");
        selection.tell("helloworld", ActorRef.noSender());

    }
}
