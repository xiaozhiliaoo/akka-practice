package org.lili.akka.learningakka.ch1;

/**
 * @author lili
 * @date 2021/5/25 0:11
 */

import akka.actor.AbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import org.lili.akka.learningakka.common.SetRequest;

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
                    map.put(message.getKey(), message.getValue());
                }).
                matchAny(o -> log.info("received unknown message: {}", o)).build();
    }
}