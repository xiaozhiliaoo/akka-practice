package org.lili.akka.learningakka.ch2;

import akka.actor.*;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import org.lili.akka.common.GetRequest;
import org.lili.akka.common.KeyNotFoundException;
import org.lili.akka.common.SetRequest;


import java.util.HashMap;
import java.util.Map;

public class AkkademyDb extends AbstractActor {
    protected final LoggingAdapter log = Logging.getLogger(context().system(), this);
    protected final Map<String, Object> map = new HashMap<String, Object>();

    @Override
    public Receive createReceive() {
        return receiveBuilder().
                match(SetRequest.class, message -> {
                    log.info("Received Set request: {}", message);
                    map.put(message.key, message.value);
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
        ActorSystem system = ActorSystem.create("akkademy");
        ActorRef actor = system.actorOf(Props.create(AkkademyDb.class), "akkademy-db");
    }
}