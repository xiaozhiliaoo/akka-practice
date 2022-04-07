package org.lili.akka.learningakka.ch2;

import akka.actor.ActorSelection;
import akka.actor.ActorSystem;
import com.typesafe.config.ConfigFactory;
import org.lili.akka.learningakka.common.GetRequest;
import org.lili.akka.learningakka.common.SetRequest;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import static akka.pattern.Patterns.ask;
import static scala.compat.java8.FutureConverters.toJava;

public class AkkademyDbClient {

    private final ActorSystem system = ActorSystem.create("LocalSystem",
            ConfigFactory.load("db-client.conf"));
    private final ActorSelection remoteDb;
    private static final int TIMEOUT = 5000;

    public AkkademyDbClient(String remoteAddress) {
        //  context.actorSelection("akka.tcp://actorSystemName@10.0.0.1:2552/user/actorName")
        remoteDb = system.actorSelection("akka.tcp://akkademy@" + remoteAddress + "/user/akkademy-db");
    }

    public CompletionStage set(String key, Object value) {
        return toJava(ask(remoteDb, new SetRequest(key, value), TIMEOUT));
    }

    public CompletionStage<Object> get(String key) {
        return toJava(ask(remoteDb, new GetRequest(key), TIMEOUT));
    }


    public static void main(String[] args) throws Exception {
        AkkademyDbClient client = new AkkademyDbClient("127.0.0.1:2552");
        client.set("key-a", "value-a");
        String result = (String) ((CompletableFuture) client.get("key-a")).get();
        System.out.println("result is:" + result);
    }
}
