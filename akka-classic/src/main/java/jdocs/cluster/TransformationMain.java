/*
 * Copyright (C) 2018-2022 Lightbend Inc. <https://www.lightbend.com>
 */

package jdocs.cluster;

import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.cluster.Cluster;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class TransformationMain {

    public static void main(String[] args) {
        //一个节点上起了三个frontend，起了2个backend
        startup("backend", 25251);
        startup("backend", 25252);
        startup("frontend", 0);
        startup("frontend", 0);
        startup("frontend", 0);
    }

    private static void startup(String role, int port) {

        // Override the configuration of the port
        Map<String, Object> overrides = new HashMap<>();
        overrides.put("akka.remote.artery.canonical.port", port);
        overrides.put("akka.cluster.roles", Collections.singletonList(role));

        Config config = ConfigFactory.parseMap(overrides)
                .withFallback(ConfigFactory.load("transformation"));

        ActorSystem system = ActorSystem.create("ClusterSystem", config);

        Cluster cluster = Cluster.get(system);

        if (cluster.selfMember().hasRole("backend")) {
            system.actorOf(Props.create(TransformationBackend.class), "transformationBackend:" + port);
        }

        if (cluster.selfMember().hasRole("frontend")) {
            system.actorOf(Props.create(TransformationFrontend.class), "transformationFrontend:" + port);
        }


    }
}

