/*
 * Copyright (C) 2018-2022 Lightbend Inc. <https://www.lightbend.com>
 */

package org.lili.akka.classic.cluster;

import akka.actor.AbstractActor;
import akka.actor.ActorSystem;
import akka.actor.Address;
import akka.actor.Props;
import akka.cluster.Cluster;
import akka.cluster.ClusterEvent;
import akka.cluster.ClusterEvent.MemberEvent;
import akka.cluster.ClusterEvent.MemberRemoved;
import akka.cluster.ClusterEvent.MemberUp;
import akka.cluster.ClusterEvent.UnreachableMember;
import akka.cluster.Member;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import java.util.Set;

public class SimpleClusterListener2 extends AbstractActor {

    LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);
    Cluster cluster = Cluster.get(getContext().getSystem());

    // subscribe to cluster changes
    @Override
    public void preStart() {
        // #subscribe
        cluster.subscribe(
                getSelf(),
                ClusterEvent.initialStateAsEvents(),
                MemberEvent.class, UnreachableMember.class);

    }

    // re-subscribe when restart
    @Override
    public void postStop() {
        cluster.unsubscribe(getSelf());
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(
                        MemberUp.class,
                        mUp -> {
                            log.info("Member is Up: {}", mUp.member());
                        })
                .match(
                        UnreachableMember.class,
                        mUnreachable -> {
                            log.info("Member detected as unreachable: {}", mUnreachable.member());
                        })
                .match(
                        MemberRemoved.class,
                        mRemoved -> {
                            log.info("Member is Removed: {}", mRemoved.member());
                        })
                .match(
                        MemberEvent.class,
                        message -> {
                            // ignore
                        })
                .build();
    }

    public static void main(String[] args) {
        String port = "2552";
        //1. get config
        Config config = ConfigFactory.parseString("akka.remote.artery.canonical.port=" + port).
                withFallback(ConfigFactory.load("SimpleClusterListener.conf"));
        //2. create akka system by config
        ActorSystem system = ActorSystem.create("ClusterSystem", config);
        //3. create akka actor
        system.actorOf(Props.create(SimpleClusterListener2.class), "SimpleClusterListener:" + port);
        System.out.println("Starting SimpleClusterListener Server ...");

        ClusterEvent.CurrentClusterState state = Cluster.get(system).state();
        Address leader = state.getLeader();
        Iterable<Member> members = state.getMembers();
        Set<Member> unreachable = state.getUnreachable();
        System.out.println("leader is:" + leader);
        System.out.println("members is:" + members);
        System.out.println("unreachable is:" + unreachable);
    }
}
