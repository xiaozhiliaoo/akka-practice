package ch8.cluster;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.pattern.Patterns;
import akka.util.Timeout;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import lombok.SneakyThrows;
import scala.concurrent.Await;
import scala.concurrent.Future;
import scala.concurrent.duration.Duration;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;


public class StartFrontendRouter {

    @SneakyThrows
    public static void main(String[] args) {
        String port = "2550";
        Config config = ConfigFactory.parseString("akka.remote.artery.canonical.port=" + port)
                .withFallback(ConfigFactory.parseString("akka.cluster.roles=[wordFrontend]"))
                .withFallback(ConfigFactory.load("wordcount.conf"));
        ActorSystem system = ActorSystem.create("sys", config);
        ActorRef ref = system.actorOf(Props.create(WordFrontRouterService.class), "wordFrontService");

        List<Article> arts = new ArrayList<>();
        for (int i = 0; i < 100000; i++) {
            arts.add(new Article(String.valueOf(new Random().nextInt(100)), "Article1 Article1"));
        }


        Object res = null;
        Timeout timeout = new Timeout(Duration.create(3, TimeUnit.SECONDS));
        for (Article art : arts) {
            Future<Object> future = Patterns.ask(ref, art, timeout);
            try {
                res = Await.result(future, timeout.duration());
                System.out.println("count result:" + res);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
