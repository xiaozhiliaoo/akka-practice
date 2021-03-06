package ch8.cluster;

import akka.actor.AbstractActor;
import akka.cluster.Cluster;
import akka.cluster.ClusterEvent;
import akka.cluster.Member;

public class WordCountService extends AbstractActor {

    Cluster cluster = Cluster.get(getContext().system());

    @Override
    public void preStart() throws Exception {
        cluster.subscribe(getSelf(), ClusterEvent.MemberUp.class);
    }

    @Override
    public void postStop() throws Exception {
        cluster.unsubscribe(getSelf());
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(Article.class, message -> {
                    System.out.println("当前节点：" + cluster.selfAddress() + ", self=" + getSelf() + " 正在处理......");
                    Article art = (Article) message;
                    int wordCount = art.getContent().split(" ").length;
                    getSender().tell(new CountResult(art.getId(), wordCount), getSelf());
                })
                .match(ClusterEvent.MemberUp.class, message -> {
                    ClusterEvent.MemberUp mu = (ClusterEvent.MemberUp) message;
                    Member m = mu.member();
                    if (m.hasRole("wordFrontend")) {
                        getContext().actorSelection(m.address() + "/user/wordFrontService").tell("serviceIsOK", getSelf());
                    }
                    System.out.println(m + " is up");
                })
                .matchAny(message -> {
                    unhandled(message);
                })
                .build();
    }
}
