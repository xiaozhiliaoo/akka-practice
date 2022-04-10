package akka.cluster.ddata;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;

import java.time.Duration;

/**
 * @author lili
 * @date 2022/4/10 21:48
 */
public class DemonstrateDelete extends AbstractActor {
    final ActorRef replicator = DistributedData.get(getContext().getSystem()).replicator();

    final Key<PNCounter> counter1Key = PNCounterKey.create("counter1");
    final Key<ORSet<String>> set2Key = ORSetKey.create("set2");

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .matchEquals(
                        "demonstrate delete",
                        msg -> {
                            replicator.tell(
                                    new Replicator.Delete<PNCounter>(counter1Key, Replicator.writeLocal()), getSelf());

                            final Replicator.WriteConsistency writeMajority = new Replicator.WriteMajority(Duration.ofSeconds(5));
                            replicator.tell(new Replicator.Delete<PNCounter>(counter1Key, writeMajority), getSelf());
                        })
                .build();
    }
}
