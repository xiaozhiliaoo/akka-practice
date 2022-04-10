package ch8;

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
import java.util.concurrent.TimeUnit;


public class StartFrontend {

    @SneakyThrows
    public static void main(String[] args) {
        String port = "2550";
        Config config = ConfigFactory.parseString("akka.remote.artery.canonical.port=" + port)
                .withFallback(ConfigFactory.parseString("akka.cluster.roles=[wordFrontend]"))
                .withFallback(ConfigFactory.load("wordcount.conf"));
        ActorSystem system = ActorSystem.create("sys", config);
        ActorRef ref = system.actorOf(Props.create(WordFrontService.class), "wordFrontService");

        String result = "";
        while (true) {
            Thread.sleep(1000);
            System.out.println("==================prepare ready==================");
            Future<Object> fu = Patterns.ask(ref, "isReady", 1000);
            try {
                result = (String) Await.result(fu, Duration.create(1000, "seconds"));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            if (result.equals("ready")) {
                System.out.println("==================ready done==================");
                break;
            }
        }

        List<Article> arts = new ArrayList<>();
        arts.add(new Article("1","Article1 Article1"));
        arts.add(new Article("2","Article12 Article2"));
        arts.add(new Article("3","Article13 Article3"));

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
