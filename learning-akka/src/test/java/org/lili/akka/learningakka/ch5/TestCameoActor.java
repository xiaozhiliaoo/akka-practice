package org.lili.akka.learningakka.ch5;

import akka.actor.AbstractActor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Complete future after 2000 messages. Used for testing.
 */
public class TestCameoActor extends AbstractActor {
    private final CompletableFuture futureToComplete;
    private List<String> articles = new ArrayList<String>();

    @Override
    public Receive createReceive() {
        return receiveBuilder().
                match(String.class, x -> {
                            articles.add(x);
                            if (articles.size() == 2000) {
                                futureToComplete.complete("OK!");
                            }
                        }
                ).build();
    }

    private TestCameoActor(CompletableFuture futureToComplete) {
        this.futureToComplete = futureToComplete;
    }
}
