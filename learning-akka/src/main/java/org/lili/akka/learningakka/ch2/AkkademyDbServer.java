package org.lili.akka.learningakka.ch2;

import akka.actor.AbstractActor;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.actor.Status;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.lili.akka.learningakka.common.GetRequest;
import org.lili.akka.learningakka.common.KeyNotFoundException;
import org.lili.akka.learningakka.common.SetRequest;

import java.util.HashMap;
import java.util.Map;

public class AkkademyDbServer extends AbstractActor {
    protected final LoggingAdapter log = Logging.getLogger(context().system(), this);
    protected final Map<String, Object> map = new HashMap<String, Object>();

    @Override
    public Receive createReceive() {
        return receiveBuilder().
                match(SetRequest.class, message -> {
                    log.info("Received Set request: {}", message);
                    map.put(message.key, message.value);
                    //给sender()的那个ActorRef发消息
                    sender().tell(new Status.Success(message.key), self());
                }).
                match(GetRequest.class, message -> {
                    log.info("Received Get request: {}", message);
                    Object value = map.get(message.key);
                    Object response = (value != null)
                            ? value
                            : new Status.Failure(new KeyNotFoundException(message.key));
                    sender().tell(response, self());
                }).
                matchAny(o ->
                        sender().tell(new Status.Failure(new ClassNotFoundException()), self())
                ).build();
    }


    public static void main(String... args) {
        Config config = ConfigFactory.load("db-server.conf");
        System.out.println("hostname:" + config.getString("akka.remote.netty.tcp.hostname"));
        System.out.println("port:" + config.getNumber("akka.remote.netty.tcp.port"));

        ActorSystem system = ActorSystem.create("akkademy", config);
        system.actorOf(Props.create(AkkademyDbServer.class), "akkademy-db");
        System.out.println("Starting DB Server ...");
    }
}