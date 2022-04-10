package ch8;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.cluster.routing.ClusterRouterPool;
import akka.cluster.routing.ClusterRouterPoolSettings;
import akka.routing.RoundRobinPool;

public class WordFrontRouterService extends AbstractActor {

    private ActorRef masterRouter;

    @Override
    public void preStart() throws Exception, Exception {
        int totalInstance = 100;
        int maxInstancesPerNode = 5;
        boolean allowLocalRoutees = false;
        String useRole = null;
        masterRouter = getContext().actorOf(
                new ClusterRouterPool(
                        new RoundRobinPool(2),
                        new ClusterRouterPoolSettings(totalInstance, maxInstancesPerNode, allowLocalRoutees, useRole)
                ).props(Props.create(WordCountService.class)), "poolRouter");
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(Article.class, message -> {
                    masterRouter.tell((Article) message, getSender());
                })
                .matchAny(message -> {
                    unhandled(message);
                })
                .build();
    }
}
