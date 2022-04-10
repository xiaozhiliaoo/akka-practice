package ch6.extension;

import akka.actor.ActorSystem;
import akka.cluster.Cluster;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

/**
 * @author lili
 * @date 2022/4/10 16:10
 */
public class Main {
    public static void main(String[] args) {
        Config config = ConfigFactory.load("extension.conf");
        ActorSystem system = ActorSystem.create("extension", config);
        RPCExtension extension = RPCExtProvider.getInstance().get(system);
        extension.rpcCall("go!");
    }
}
