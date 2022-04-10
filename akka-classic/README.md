# Step1

```
cd akka-classic
mvn package
```

# Step2

## SimpleClusterListener

```
java -jar target\akka-classic-1.0-SNAPSHOT-allinone.jar 2550
java -jar target\akka-classic-1.0-SNAPSHOT-allinone.jar 2551
java -jar target\akka-classic-1.0-SNAPSHOT-allinone.jar 2552
```

## StatsSampleOneMasterMain

```
java -cp target\akka-classic-1.0-SNAPSHOT-allinone.jar jdocs.cluster.StatsSampleOneMasterMain 2551
java -cp target\akka-classic-1.0-SNAPSHOT-allinone.jar jdocs.cluster.StatsSampleOneMasterMain 2552
java -cp target\akka-classic-1.0-SNAPSHOT-allinone.jar jdocs.cluster.StatsSampleOneMasterMain 0
```

## DistributedPubSubMediatorMain

```
java -cp target\akka-classic-1.0-SNAPSHOT-allinone.jar akka.cluster.pubsub.DistributedPubSubMediatorMain 2551 sub
java -cp target\akka-classic-1.0-SNAPSHOT-allinone.jar akka.cluster.pubsub.DistributedPubSubMediatorMain 2552 sub
java -cp target\akka-classic-1.0-SNAPSHOT-allinone.jar akka.cluster.pubsub.DistributedPubSubMediatorMain 2553 sub
java -cp target\akka-classic-1.0-SNAPSHOT-allinone.jar akka.cluster.pubsub.DistributedPubSubMediatorMain 2554 pub "I am Happy"
```
