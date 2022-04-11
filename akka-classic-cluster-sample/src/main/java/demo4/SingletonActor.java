package demo4;

import akka.actor.AbstractActor;
import akka.cluster.Cluster;
import akka.cluster.ClusterEvent;
import akka.cluster.Member;

public class SingletonActor extends AbstractActor {

    Cluster cluster = Cluster.get(getContext().system());


    @Override
    public void preStart() throws Exception {
        cluster.subscribe(getSelf(), ClusterEvent.MemberUp.class);
    }


    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(ClusterEvent.MemberUp.class, message -> {
                    System.out.println("SingletonActor:" + getSelf());
                    ClusterEvent.MemberUp mu = (ClusterEvent.MemberUp) message;
                    Member m = mu.member();
                    System.out.println(m + " is up[singleton actor start]");
                })
                .build();
    }
}
