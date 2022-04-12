package demo6.book;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.cluster.sharding.ClusterSharding;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import lombok.SneakyThrows;

public class UserCliActor extends AbstractActor {

    @Override
    public Receive createReceive() {

        ActorRef userRegion = ClusterSharding.get(getContext().system()).shardRegion("userActor");

        return receiveBuilder()

                .matchEquals("buy", msg -> {
                    Cmd cmd0 = new Cmd(Cmd.BUY, 0);
                    cmd0.setItem(new Item(0, "JAVA编程思想", 80));
                    userRegion.tell(cmd0, getSelf());

                    Cmd cmd1 = new Cmd(Cmd.BUY, 1);
                    cmd1.setItem(new Item(1, "C++编程思想", 90));
                    userRegion.tell(cmd1, getSelf());

                    Cmd cmd2 = new Cmd(Cmd.BUY, 2);
                    cmd2.setItem(new Item(2, "AKKA实战", 20));
                    userRegion.tell(cmd2, getSelf());
                })
                .matchEquals("query", msg -> {
                    userRegion.tell(new Cmd(Cmd.QUERY, 0), getSelf());
                    userRegion.tell(new Cmd(Cmd.QUERY, 1), getSelf());
                    userRegion.tell(new Cmd(Cmd.QUERY, 2), getSelf());
                })
                .build();
    }

    @SneakyThrows
    public static void main(String[] args) {
        String port = "2553";
        Config config = ConfigFactory.parseString("akka.remote.artery.canonical.port=" + port).
                withFallback(ConfigFactory.load("cart.conf"));
        ActorSystem system = ActorSystem.create("sys", config);
        Thread.sleep(10000);
        ActorRef ref = system.actorOf(Props.create(UserCliActor.class), "cli");
        Thread.sleep(10000);
        ref.tell("buy", ActorRef.noSender());
        ref.tell("query", ActorRef.noSender());
    }
}
