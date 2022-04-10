package ch8;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Terminated;

import java.util.ArrayList;
import java.util.List;

public class WordFrontService extends AbstractActor {

    private List<ActorRef> wordCountServices = new ArrayList<>();

    private int jobCounter = 0;

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(Article.class, message -> {
                    jobCounter++;
                    Article art = (Article) message;
                    int serviceNodeIndex = jobCounter % wordCountServices.size();
                    System.out.println("选择节点：" + serviceNodeIndex);
                    wordCountServices.get(serviceNodeIndex).forward(art, getContext());
                })
                .match(String.class, message -> {
                    String cmd = (String) message;
                    if (cmd.equals("serviceIsOK")) {
                        ActorRef backendSender = getSender();
                        System.out.println(backendSender + " 可用");
                        wordCountServices.add(backendSender);
                        getContext().watch(backendSender);
                    } else if (cmd.equals("isReady")) {
                        if (!wordCountServices.isEmpty()) {
                            getSender().tell("ready", getSelf());
                        } else {
                            getSender().tell("notReady", getSelf());
                        }
                    }
                })
                .match(Terminated.class, message -> {
                    Terminated ter = (Terminated) message;
                    System.out.println("移除了 " + ter.getActor());
                    wordCountServices.remove(ter.getActor());
                })
                .matchAny(message -> {
                    unhandled(message);
                })
                .build();
    }
}
