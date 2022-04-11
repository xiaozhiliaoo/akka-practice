package demo6.book;

import akka.actor.PoisonPill;
import akka.actor.ReceiveTimeout;
import akka.cluster.Cluster;
import akka.cluster.sharding.ShardRegion;
import akka.japi.Procedure;
import akka.persistence.AbstractPersistentActor;
import scala.concurrent.duration.Duration;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class UserActor extends AbstractPersistentActor {
    private Map<Integer, Item> cart = new HashMap<>();

    Cluster cluster = Cluster.get(getContext().system());

    public UserActor() {
        context().setReceiveTimeout(Duration.create(60, TimeUnit.SECONDS));
    }

    @Override
    public void preStart() throws Exception, Exception {
        super.preStart();
        System.out.println(cluster.selfAddress() + "." + getSelf() + " start");
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(Cmd.class, msg -> {
                    System.out.println("cmd: " + msg);
                    Cmd cmd = (Cmd) msg;
                    if (cmd.getAction().equals(Cmd.QUERY)) {
                        getSender().tell(cmd.getUserId() + " cart:" + cart, getSelf());
                    } else {
                        persist(cmd, new Procedure<Cmd>() {
                            @Override
                            public void apply(Cmd param) throws Exception, Exception {
                                handleCmd(param);
                            }
                        });
                    }
                })
                .match(ReceiveTimeout.class, msg -> {
                    getContext().parent().tell(new ShardRegion.Passivate(PoisonPill.getInstance()), getSelf());

                })
                .matchAny(msg -> {
                    System.out.println("Other msg: " + msg);

                })
                .build();
    }

    @Override
    public Receive createReceiveRecover() {
        return receiveBuilder()
                .match(Cmd.class, msg -> {
                    Cmd cmd = (Cmd) msg;
                    handleCmd(cmd);
                })
                .build();
    }

    @Override
    public String persistenceId() {
        return "user_" + getSelf().path().name();
    }

    private void handleCmd(Cmd cmd) {
        if (cmd.getAction().equals(Cmd.BUY)) {
            Item item = cmd.getItem();
            cart.put(item.getId(), item);
        } else if (cmd.getAction().equals(Cmd.DEL)) {
            Item item = cmd.getItem();
            cart.remove(item.getId());
        } else {
            System.out.println("Other handlerCmd: " + cmd);
        }
    }

}
