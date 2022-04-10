package ch6;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.pattern.Patterns;
import akka.util.Timeout;
import scala.concurrent.Await;
import scala.concurrent.Future;
import scala.concurrent.duration.Duration;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @author lili
 * @date 2022/4/10 15:48
 */
public class FutureActor extends AbstractActor {
    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .matchAny(msg -> {
                    getSender().tell("reply:" + msg, getSender());
                })
                .build();
    }


    public static void main(String[] args) {
        ActorSystem system = ActorSystem.create("sys");
        ActorRef ref = system.actorOf(Props.create(FutureActor.class));
        Timeout timeout = new Timeout(Duration.create(3, TimeUnit.SECONDS));
        Future<Object> future = Patterns.ask(ref, "hello future", timeout);
        Object result = null;
        try {
            result = Await.result(future, timeout.duration());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        System.out.println(result);
    }
}
