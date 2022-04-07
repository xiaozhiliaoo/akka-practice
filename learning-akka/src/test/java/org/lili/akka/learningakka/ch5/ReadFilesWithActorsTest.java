package org.lili.akka.learningakka.ch5;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.routing.RoundRobinPool;
import org.junit.Test;

import java.util.concurrent.CompletableFuture;
import java.util.stream.IntStream;

public class ReadFilesWithActorsTest {
    ActorSystem system = ActorSystem.create();

    @Test
    public void shouldReadFilesWithActors() throws Exception {

        ActorRef workerRouter = system.actorOf(Props.create(ArticleParseActor.class).
                withRouter(new RoundRobinPool(8)));

        CompletableFuture future = new CompletableFuture();
        ActorRef cameoActor = system.actorOf(Props.create(TestCameoActor.class, future));

        IntStream.range(0, 2000).forEach(x -> {
                    workerRouter.tell(
                            new ParseArticle(TestHelper.file)
                            , cameoActor);
                }
        );

        long start = System.currentTimeMillis();
        future.get();
        long elapsedTime = System.currentTimeMillis() - start;
        System.out.println("ReadFilesWithActorsTest Took: " + elapsedTime);

    }

}
