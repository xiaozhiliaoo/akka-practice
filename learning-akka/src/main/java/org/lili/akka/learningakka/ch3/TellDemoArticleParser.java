package org.lili.akka.learningakka.ch3;

import akka.actor.*;
import akka.util.Timeout;
import org.lili.akka.learningakka.common.GetRequest;
import org.lili.akka.learningakka.common.SetRequest;

import java.util.concurrent.TimeoutException;

public class TellDemoArticleParser extends AbstractActor {

    private final ActorSelection cacheActor;
    private final ActorSelection httpClientActor;
    private final ActorSelection artcileParseActor;
    private final Timeout timeout;

    public TellDemoArticleParser(String cacheActorPath, String httpClientActorPath, String artcileParseActorPath, Timeout timeout) {
        this.cacheActor = context().actorSelection(cacheActorPath);
        this.httpClientActor = context().actorSelection(httpClientActorPath);
        this.artcileParseActor = context().actorSelection(artcileParseActorPath);
        this.timeout = timeout;
    }

    /**
     * While this example is a bit harder to understand than the ask demo,
     * for extremely performance critical applications, this has an advantage over ask.
     * The creation of 5 objects are saved - only one extra actor is created.
     * Functionally it's similar.
     * It will make the request to the HTTP actor w/o waiting for the cache response though (can be solved).
     *
     * @return
     * @apiNote cacheActor->httpClientActor->ParsingActor
     */
    @Override
    public Receive createReceive() {
        return receiveBuilder().
                match(ParseArticle.class, msg -> {
                    //extraActor对应了cacheActor，cacheActor，timeout的响应，已经启动了extraActor了.存活时间3s.
                    ActorRef extraActor = buildExtraActor(sender(), msg.url);
                    //向缓存actor发消息，cacheActor返回String，处理完消息会返回给extraActor
                    cacheActor.tell(new GetRequest(msg.url), extraActor);
                    //向httpclient发送消息，httpClientActor会向extraActor返回HttpResponse，处理完消息会返回给extraActor
                    httpClientActor.tell(msg.url, extraActor);
                    //对extraActor进行调度，发送timeout消息
                    context().system().scheduler().scheduleOnce(timeout.duration(),
                            extraActor, "timeout", context().system().dispatcher(), ActorRef.noSender());
                }).build();
    }

    /**
     * The extra actor will collect responses from the assorted actors it interacts with.
     * The cache actor reply, the http actor reply, and the article parser reply are all handled.
     * Then the actor will shut itself down once the work is complete.
     * A great use case for the use of tell here (aka extra pattern) is aggregating data from several sources.
     */
    private ActorRef buildExtraActor(ActorRef senderRef, String uri) {

        class MyActor extends AbstractActor {

            @Override
            public Receive createReceive() {
                return receiveBuilder()
                        .matchEquals(String.class, x -> x.equals("timeout"), x -> {
                            //if we get timeout, then fail
                            senderRef.tell(new Status.Failure(new TimeoutException("timeout!")), self());
                            context().stop(self());
                        })
                        .match(HttpResponse.class, httpResponse -> {
                            //If we get the cache response first, then we handle it and shut down.
                            //The cache response will come back before the HTTP response so we never parse in this case.
                            artcileParseActor.tell(new ParseHtmlArticle(uri, httpResponse.body), self());
                        })
                        .match(String.class, body -> {
                            //If we get the cache response first, then we handle it and shut down.
                            //The cache response will come back before the HTTP response so we never parse in this case.
                            senderRef.tell(body, self());
                            context().stop(self());
                        })
                        .match(ArticleBody.class, articleBody -> {
                            //If we get the parsed article back, then we've just parsed it
                            cacheActor.tell(new SetRequest(articleBody.uri, articleBody.body), self());
                            senderRef.tell(articleBody.body, self());
                            context().stop(self());
                        })
                        .matchAny(t -> {
                            //We can get a cache miss
                            System.out.println("ignoring msg: " + t.getClass());
                        })
                        .build();
            }
        }

        return context().actorOf(Props.create(MyActor.class, new MyActor()));
    }
}




