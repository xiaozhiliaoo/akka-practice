package org.lili.akka.learningakka.ch4;

import akka.actor.AbstractActor;
import akka.actor.Status;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import org.lili.akka.learningakka.common.Connected;
import org.lili.akka.learningakka.common.GetRequest;
import org.lili.akka.learningakka.common.KeyNotFoundException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AkkademyDb extends AbstractActor {
    protected final LoggingAdapter log = Logging.getLogger(context().system(), this);
    public final Map<String, Object> map = new HashMap<String, Object>();

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(Connected.class, message -> {
                    log.info("Received Connected request: {}", message);
                    sender().tell(new Connected(), self());
                })
                .match(List.class, message -> {
                            message.forEach(x -> {
                                if (x instanceof SetRequest) {
                                    SetRequest setRequest = (SetRequest) x;
                                    handleSetRequest(setRequest);
                                }
                                if (x instanceof GetRequest) {
                                    GetRequest getRequest = (GetRequest) x;
                                    handleGetRequest(getRequest);
                                }
                            });
                        }
                )
                .match(SetRequest.class, message -> {
                    handleSetRequest(message);
                })
                .match(GetRequest.class, message -> {
                    handleGetRequest(message);
                })
                .matchAny(o -> {
                    log.info("unknown message: " + o);
                    sender().tell(new Status.Failure(new ClassNotFoundException()), self());
                }).build();

    }

    private void handleSetRequest(SetRequest message) {
        log.info("Received Set request: {}", message);
        map.put(message.key, message.value);
        message.sender.tell(new Status.Success(message.key), self());
    }

    private void handleGetRequest(GetRequest getRequest) {
        log.info("Received Get request: {}", getRequest);
        Object value = map.get(getRequest.key);
        Object response = (value != null)
                ? value
                : new Status.Failure(new KeyNotFoundException(getRequest.key));
        sender().tell(response, self());
    }
}