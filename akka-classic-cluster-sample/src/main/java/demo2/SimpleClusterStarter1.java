/*
 * Copyright (C) 2018-2022 Lightbend Inc. <https://www.lightbend.com>
 */

package demo2;

import akka.actor.ActorSystem;
import akka.actor.Address;
import akka.actor.Props;
import akka.cluster.Cluster;
import akka.cluster.ClusterEvent;
import akka.cluster.Member;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import java.util.Set;

public class SimpleClusterStarter1 {

    public static void main(String[] args) {
        String port = "2551";

        //1. get config
        Config config = ConfigFactory.parseString("akka.remote.artery.canonical.port=" + port).
                withFallback(ConfigFactory.load("SimpleClusterListener.conf"));
        //2. create akka system by config
        ActorSystem system = ActorSystem.create("ClusterSystem", config);
        //3. create akka actor
        system.actorOf(Props.create(SimpleClusterActor.class), "SimpleClusterStarter:" + port);
        System.out.println("Starting SimpleClusterStarter1 Server ...");

        ClusterEvent.CurrentClusterState state = Cluster.get(system).state();
        Address leader = state.getLeader();
        Iterable<Member> members = state.getMembers();
        Set<Member> unreachable = state.getUnreachable();
        System.out.println("leader is:" + leader);
        System.out.println("members is:" + members);
        System.out.println("unreachable is:" + unreachable);
    }
}
