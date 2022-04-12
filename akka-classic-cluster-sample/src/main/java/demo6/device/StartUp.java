package demo6.device;

import akka.actor.ActorSystem;
import akka.actor.Props;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

/**
 * @author lili
 * @date 2022/4/12 20:54
 */
public class StartUp {

    public static void run(String port) {
        // Override the configuration of the port
        Config config = ConfigFactory.parseString(
                "akka.remote.artery.canonical.port=" + port).withFallback(
                ConfigFactory.load("device.conf"));

        // Create an Akka system
        ActorSystem system = ActorSystem.create("ShardingSystem", config);

        // Create an actor that starts the sharding and sends random messages
        system.actorOf(Props.create(Devices.class));
    }
}
