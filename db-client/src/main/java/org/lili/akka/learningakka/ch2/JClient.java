package org.lili.akka.learningakka.ch2;

/**
 * @author lili
 * @date 2021/5/25 0:52
 */

import akka.actor.ActorSelection;
import akka.actor.ActorSystem;
import akka.pattern.Patterns;
import org.lili.akka.common.GetRequest;
import org.lili.akka.common.SetRequest;


import java.util.concurrent.CompletionStage;

import static akka.pattern.Patterns.ask;
import static scala.compat.java8.FutureConverters.toJava;

public class JClient {
    private final ActorSystem system = ActorSystem.create("LocalSystem");
    private final ActorSelection remoteDb;

    public JClient(String remoteAddress) {
        remoteDb = system.actorSelection("akka.tcp://akkademy@" + remoteAddress + "/user/akkademy-db");
    }

    public CompletionStage set(String key, Object value) {
        return toJava(Patterns.ask(remoteDb, new SetRequest(key, value), 2000));
    }

    public CompletionStage<Object> get(String key) {
        return toJava(Patterns.ask(remoteDb, new GetRequest(key), 2000));
    }
}
