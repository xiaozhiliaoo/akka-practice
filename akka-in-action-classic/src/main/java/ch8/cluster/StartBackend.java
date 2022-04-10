package ch8.cluster;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

public class StartBackend {
    public static void main(String[] args) {
        String port = "2551";
        Config config = ConfigFactory.parseString("akka.remote.artery.canonical.port=" + port)
                .withFallback(ConfigFactory.parseString("akka.cluster.roles=[wordBackend]"))
                .withFallback(ConfigFactory.load("wordcount.conf"));
        ActorSystem system = ActorSystem.create("sys", config);
        ActorRef ref = system.actorOf(Props.create(WordCountService.class), "wordCountService" + port);
    }
}
