package akka.cluster.ddata;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.japi.pf.ReceiveBuilder;

import java.time.Duration;

/**
 * @author lili
 * @date 2022/4/10 21:44
 */
public class DemonstrateUpdate extends AbstractActor {
    final SelfUniqueAddress node =
            DistributedData.get(getContext().getSystem()).selfUniqueAddress();
    final ActorRef replicator = DistributedData.get(getContext().getSystem()).replicator();

    final Key<PNCounter> counter1Key = PNCounterKey.create("counter1");
    final Key<GSet<String>> set1Key = GSetKey.create("set1");
    final Key<ORSet<String>> set2Key = ORSetKey.create("set2");
    final Key<Flag> activeFlagKey = FlagKey.create("active");

    @Override
    public Receive createReceive() {
        ReceiveBuilder b = receiveBuilder();

        b.matchEquals(
                "demonstrate update",
                msg -> {
                    replicator.tell(
                            new Replicator.Update<PNCounter>(
                                    counter1Key,
                                    PNCounter.create(),
                                    Replicator.writeLocal(),
                                    curr -> curr.increment(node, 1)),
                            getSelf());

                    final Replicator.WriteConsistency writeTo3 = new Replicator.WriteTo(3, Duration.ofSeconds(1));
                    replicator.tell(
                            new Replicator.Update<GSet<String>>(
                                    set1Key, GSet.create(), writeTo3, curr -> curr.add("hello")),
                            getSelf());

                    final Replicator.WriteConsistency writeMajority = new Replicator.WriteMajority(Duration.ofSeconds(5));
                    replicator.tell(
                            new Replicator.Update<ORSet<String>>(
                                    set2Key, ORSet.create(), writeMajority, curr -> curr.add(node, "hello")),
                            getSelf());

                    final Replicator.WriteConsistency writeAll = new Replicator.WriteAll(Duration.ofSeconds(5));
                    replicator.tell(
                            new Replicator.Update<Flag>(
                                    activeFlagKey, Flag.create(), writeAll, curr -> curr.switchOn()),
                            getSelf());
                });
        // #update

        // #update-response1
        b.match(
                Replicator.UpdateSuccess.class,
                a -> a.key().equals(counter1Key),
                a -> {
                    // ok
                });
        // #update-response1

        // #update-response2
        b.match(
                        Replicator.UpdateSuccess.class,
                        a -> a.key().equals(set1Key),
                        a -> {
                            // ok
                        })
                .match(
                        Replicator.UpdateTimeout.class,
                        a -> a.key().equals(set1Key),
                        a -> {
                            // write to 3 nodes failed within 1.second
                        });
        // #update-response2

        // #update
        return b.build();
    }

    public static void main(String[] args) {
        System.out.println(111);
    }
}