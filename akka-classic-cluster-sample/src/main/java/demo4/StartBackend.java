package demo4;

import akka.actor.ActorSystem;
import akka.actor.PoisonPill;
import akka.actor.Props;
import akka.cluster.singleton.ClusterSingletonManager;
import akka.cluster.singleton.ClusterSingletonManagerSettings;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

public class StartBackend {
    public static void main(String[] args) {
        String port = "2551";
        Config config = ConfigFactory.parseString("akka.remote.artery.canonical.port=" + port)
                .withFallback(ConfigFactory.parseString("akka.cluster.roles=[wordBackend]"))
                .withFallback(ConfigFactory.load("wordcount.conf"));
        ActorSystem system = ActorSystem.create("sys", config);
        ClusterSingletonManagerSettings settings = ClusterSingletonManagerSettings.create(system);
        system.actorOf(ClusterSingletonManager.props(Props.create(SingletonActor.class), PoisonPill.getInstance(), settings), "singleActor");

    }
}
