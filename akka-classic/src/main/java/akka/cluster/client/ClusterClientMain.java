/*
 * Copyright (C) 2009-2022 Lightbend Inc. <https://www.lightbend.com>
 */

package akka.cluster.client;

import akka.actor.*;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import java.util.*;

public class ClusterClientMain {


    // #initialContacts
    static Set<ActorPath> initialContacts() {
        return new HashSet<ActorPath>(
                Arrays.asList(
                        ActorPaths.fromString("akka://OtherSys@host1:2552/system/receptionist"),
                        ActorPaths.fromString("akka://OtherSys@host2:2552/system/receptionist")));
    }
    // #initialContacts

    public static void main(String[] args) {

        Map<String, Object> overrides = new HashMap<>();
        Config config = ConfigFactory.parseMap(overrides)
                .withFallback(ConfigFactory.load("ClusterClient.conf"));

        ActorSystem system = ActorSystem.create("ClusterSystem", config);
        // #server
        ActorRef serviceA = system.actorOf(Props.create(Service.class), "serviceA");
        ClusterClientReceptionist.get(system).registerService(serviceA);

        ActorRef serviceB = system.actorOf(Props.create(Service.class), "serviceB");
        ClusterClientReceptionist.get(system).registerService(serviceB);
        // #server

        // #client
        final ActorRef c =
                system.actorOf(
                        ClusterClient.props(
                                ClusterClientSettings.create(system).withInitialContacts(initialContacts())),
                        "client");
        c.tell(new ClusterClient.Send("/user/serviceA", "hello", true), ActorRef.noSender());
        c.tell(new ClusterClient.SendToAll("/user/serviceB", "hi"), ActorRef.noSender());
        // #client

        system.actorOf(Props.create(ClientListener.class, c));
        system.actorOf(
                Props.create(
                        ReceptionistListener.class, ClusterClientReceptionist.get(system).underlying()));
    }
}

class Service extends UntypedAbstractActor {
    public void onReceive(Object msg) {
    }
}

// #clientEventsListener
class ClientListener extends AbstractActor {
    private final ActorRef targetClient;
    private final Set<ActorPath> contactPoints = new HashSet<>();

    public ClientListener(ActorRef targetClient) {
        this.targetClient = targetClient;
    }

    @Override
    public void preStart() {
        targetClient.tell(SubscribeContactPoints.getInstance(), sender());
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(
                        ContactPoints.class,
                        msg -> {
                            contactPoints.addAll(msg.getContactPoints());
                            // Now do something with an up-to-date "contactPoints"
                        })
                .match(
                        ContactPointAdded.class,
                        msg -> {
                            contactPoints.add(msg.contactPoint());
                            // Now do something with an up-to-date "contactPoints"
                        })
                .match(
                        ContactPointRemoved.class,
                        msg -> {
                            contactPoints.remove(msg.contactPoint());
                            // Now do something with an up-to-date "contactPoints"
                        })
                .build();
    }
}
// #clientEventsListener

// #receptionistEventsListener
class ReceptionistListener extends AbstractActor {
    private final ActorRef targetReceptionist;
    private final Set<ActorRef> clusterClients = new HashSet<>();

    public ReceptionistListener(ActorRef targetReceptionist) {
        this.targetReceptionist = targetReceptionist;
    }

    @Override
    public void preStart() {
        targetReceptionist.tell(SubscribeClusterClients.getInstance(), sender());
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(
                        ClusterClients.class,
                        msg -> {
                            clusterClients.addAll(msg.getClusterClients());
                            // Now do something with an up-to-date "clusterClients"
                        })
                .match(
                        ClusterClientUp.class,
                        msg -> {
                            clusterClients.add(msg.clusterClient());
                            // Now do something with an up-to-date "clusterClients"
                        })
                .match(
                        ClusterClientUnreachable.class,
                        msg -> {
                            clusterClients.remove(msg.clusterClient());
                            // Now do something with an up-to-date "clusterClients"
                        })
                .build();
    }
}
// #receptionistEventsListener

