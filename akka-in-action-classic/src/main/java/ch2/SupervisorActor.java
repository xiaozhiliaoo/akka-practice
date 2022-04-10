package ch2;

import akka.actor.*;
import akka.japi.pf.DeciderBuilder;
import scala.Option;

import java.io.IOException;
import java.sql.SQLException;
import java.time.Duration;
import java.util.Optional;


class WorkerActor extends AbstractActor {
    private int stateCount = 1;


    @Override
    public void preStart() throws Exception {
        super.preStart();
        System.out.println("worker actor preStart:" + getContext().getSelf() + ",parent:" + getContext().getParent());
    }

    @Override
    public void postStop() throws Exception {
        super.postStop();
        System.out.println("worker actor postStop:" + getContext().getSelf() + ",parent:" + getContext().getParent());
    }

    @Override
    public void preRestart(Throwable reason, Option<Object> message) throws Exception, Exception {
        System.out.println("worker actor preRestart begin " + this.stateCount + " " + getContext().getSelf() + ",parent:" + getContext().getParent());
        super.preRestart(reason, message);
        System.out.println("work actor preRestart end " + this.stateCount + " " + getContext().getSelf() + ",parent:" + getContext().getParent());
    }

    @Override
    public void postRestart(Throwable reason) throws Exception, Exception {
        System.out.println("worker actor postRestart begin " + this.stateCount + " " + getContext().getSelf() + ",parent:" + getContext().getParent());
        super.postRestart(reason);
        System.out.println("worker actor postRestart end " + this.stateCount + " " + getContext().getSelf() + ",parent:" + getContext().getParent());
    }

    @Override
    public Receive createReceive() {
        this.stateCount++;
        return receiveBuilder()
                .match(Exception.class, msg -> {
                    System.out.println("worker actor receive exception:" + msg.getClass());
                    throw (Exception) msg;
                })
                .matchEquals("getValue", msg -> {
                    getSender().tell(stateCount, getSelf());
                })
                .matchAny(msg -> {
                    unhandled(msg);
                })
                .build();
    }
}


/**
 * @author lili03
 */
public class SupervisorActor extends AbstractActor {


    //no lifecycle method
    private static SupervisorStrategy strategy = new OneForOneStrategy(
            3,
            Duration.ofMinutes(1),
            DeciderBuilder
                    .match(IOException.class, e -> {
                        System.out.println("========== IOException  Resume =========");
                        return (SupervisorStrategy.Directive) SupervisorStrategy.resume();
                    })
                    .match(IndexOutOfBoundsException.class, e -> {
                        System.out.println("========== IndexOutOfBoundsException Restart =========");
                        return (SupervisorStrategy.Directive) SupervisorStrategy.restart();
                    })
                    .match(SQLException.class, e -> {
                        System.out.println("========== SQLException Stop =========");
                        return (SupervisorStrategy.Directive) SupervisorStrategy.stop();
                    })
                    .matchAny(o -> (SupervisorStrategy.Directive) SupervisorStrategy.escalate())
                    .build());

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(Terminated.class, msg -> {
                    Terminated ter = (Terminated) msg;
                    System.out.println(ter.getActor() + "已经终止");
                })
                .matchAny(msg -> {
                    System.out.println("stateCount=" + msg);
                })
                .build();
    }


    @Override
    public void preStart() throws Exception {
        System.out.println("supervisor actor preStart:" + getSelf() + ",parent:" + getContext().parent());
        ActorRef child = getContext().actorOf(Props.create(WorkerActor.class), "workerActor");
        getContext().watch(child);
    }


    @Override
    public void postStop() throws Exception, Exception {
        super.postStop();
        System.out.println("supervisor actor postStop:" + getSelf());

    }

    @Override
    public void preRestart(Throwable reason, Optional<Object> message) throws Exception, Exception {
        super.preRestart(reason, message);
        System.out.println("supervisor actor preRestart:" + getSelf());

    }

    @Override
    public void postRestart(Throwable reason) throws Exception, Exception {
        super.postRestart(reason);
        System.out.println("supervisor actor postRestart:" + getSelf());
    }

    @Override
    public SupervisorStrategy supervisorStrategy() {
        return strategy;
    }

    /**
     * 如何获取supervisorRef里面的child actor呢？
     *
     * @param args
     */
    public static void main(String[] args) {
        ActorSystem actorSystem = ActorSystem.create("sys");
        ActorRef supervisorRef = actorSystem.actorOf(Props.create(SupervisorActor.class), "supervisor");
        System.out.println("supervisor:" + supervisorRef);
        ActorSelection workerRef = actorSystem.actorSelection("akka://sys/user/supervisor/workerActor");
        workerRef.tell(new IndexOutOfBoundsException(), ActorRef.noSender());
//        workerRef.tell(new IOException(), ActorRef.noSender());
//        workerRef.tell(new SQLException(), ActorRef.noSender());
//        workerRef.tell("getValue", ActorRef.noSender());


//        ActorRef workerRef = actorSystem.actorOf(Props.create(workerActor.class), "workerActor");
//        System.out.println("worker actor:" + workerRef);
//        workerRef.tell(new IOException(), ActorRef.noSender());
//        workerRef.tell(new SQLException(), ActorRef.noSender());
//        workerRef.tell("getValue", ActorRef.noSender());
//        workerRef.tell(new IndexOutOfBoundsException(), ActorRef.noSender());

    }
}


