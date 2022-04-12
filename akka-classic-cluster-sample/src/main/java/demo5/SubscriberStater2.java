package demo5;

import akka.actor.ActorSystem;
import akka.actor.Props;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author lili
 * @date 2022/4/12 20:01
 */
public class SubscriberStater2 {
    public static void main(String[] args) {
        String port = "2552";
        Map<String, Object> overrides = new HashMap<>();
        overrides.put("akka.remote.artery.canonical.port", port);
        overrides.put("akka.cluster.roles", Collections.singletonList("sub"));

        Config config = ConfigFactory.parseMap(overrides)
                .withFallback(ConfigFactory.load("PubSub.conf"));

        ActorSystem system = ActorSystem.create("ClusterSystem", config);


        // #start-subscribers
        system.actorOf(Props.create(SubscriberActor.class), "subscriber:" + port);
        // #start-subscribers
    }

}
