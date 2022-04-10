package akka.cluster.ddata;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.japi.pf.ReceiveBuilder;

import java.math.BigInteger;
import java.time.Duration;
import java.util.Set;

/**
 * @author lili
 * @date 2022/4/10 21:46
 */
public class DemonstrateGet extends AbstractActor {
    final ActorRef replicator = DistributedData.get(getContext().getSystem()).replicator();

    final Key<PNCounter> counter1Key = PNCounterKey.create("counter1");
    final Key<GSet<String>> set1Key = GSetKey.create("set1");
    final Key<ORSet<String>> set2Key = ORSetKey.create("set2");
    final Key<Flag> activeFlagKey = FlagKey.create("active");

    @Override
    public Receive createReceive() {
        ReceiveBuilder b = receiveBuilder();

        b.matchEquals(
                "demonstrate get",
                msg -> {
                    replicator.tell(
                            new Replicator.Get<PNCounter>(counter1Key, Replicator.readLocal()), getSelf());

                    final Replicator.ReadConsistency readFrom3 = new Replicator.ReadFrom(3, Duration.ofSeconds(1));
                    replicator.tell(new Replicator.Get<GSet<String>>(set1Key, readFrom3), getSelf());

                    final Replicator.ReadConsistency readMajority = new Replicator.ReadMajority(Duration.ofSeconds(5));
                    replicator.tell(new Replicator.Get<ORSet<String>>(set2Key, readMajority), getSelf());

                    final Replicator.ReadConsistency readAll = new Replicator.ReadAll(Duration.ofSeconds(5));
                    replicator.tell(new Replicator.Get<Flag>(activeFlagKey, readAll), getSelf());
                });
        // #get

        // #get-response1
        b.match(
                        Replicator.GetSuccess.class,
                        a -> a.key().equals(counter1Key),
                        a -> {
                            Replicator.GetSuccess<PNCounter> g = a;
                            BigInteger value = g.dataValue().getValue();
                        })
                .match(
                        Replicator.NotFound.class,
                        a -> a.key().equals(counter1Key),
                        a -> {
                            // key counter1 does not exist
                        });
        // #get-response1

        // #get-response2
        b.match(
                        Replicator.GetSuccess.class,
                        a -> a.key().equals(set1Key),
                        a -> {
                            Replicator.GetSuccess<GSet<String>> g = a;
                            Set<String> value = g.dataValue().getElements();
                        })
                .match(
                        Replicator.GetFailure.class,
                        a -> a.key().equals(set1Key),
                        a -> {
                            // read from 3 nodes failed within 1.second
                        })
                .match(
                        Replicator.NotFound.class,
                        a -> a.key().equals(set1Key),
                        a -> {
                            // key set1 does not exist
                        });
        // #get-response2

        // #get
        return b.build();
    }

    public static void main(String[] args) {
        System.out.println(1);
    }
}