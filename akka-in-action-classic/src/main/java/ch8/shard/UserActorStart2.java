package ch8.shard;

import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.cluster.sharding.ClusterSharding;
import akka.cluster.sharding.ClusterShardingSettings;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

/**
 * @author lili
 * @date 2022/4/10 23:39
 */
public class UserActorStart2 {
    public static void main(String[] args) {
        String port = "2551";
        Config config = ConfigFactory.parseString("akka.remote.artery.canonical.port=" + port).
                withFallback(ConfigFactory.load("cart.conf"));
        //2. create akka system by config
        ActorSystem system = ActorSystem.create("sys", config);
        ClusterShardingSettings settings = ClusterShardingSettings.create(system);
        ClusterSharding.get(system).start("userActor", Props.create(UserActor.class), settings, new UserMessageExtractor());
    }
}
