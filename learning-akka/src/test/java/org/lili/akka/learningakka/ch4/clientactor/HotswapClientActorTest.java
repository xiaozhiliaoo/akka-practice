package org.lili.akka.learningakka.ch4.clientactor;

import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.actor.Status;
import akka.testkit.TestActorRef;
import akka.testkit.TestProbe;
import akka.util.Timeout;

import com.typesafe.config.ConfigFactory;
import org.junit.Test;
import org.lili.akka.learningakka.ch4.AkkademyDb;
import org.lili.akka.learningakka.ch4.SetRequest;
import org.lili.akka.learningakka.common.GetRequest;

import java.util.concurrent.TimeUnit;

public class HotswapClientActorTest {
    ActorSystem system = ActorSystem.create("testSystem", ConfigFactory.defaultReference());
    Timeout timeout = new Timeout(10000, TimeUnit.MILLISECONDS);

    @Test
    public void itShouldSet() throws Exception {
        TestActorRef<AkkademyDb> dbRef = TestActorRef.create(system, Props.create(AkkademyDb.class));
        AkkademyDb db = dbRef.underlyingActor();

        TestProbe probe = TestProbe.apply(system);
        TestActorRef<HotswapClientActor> clientRef =
                TestActorRef.create(system, Props.create(HotswapClientActor.class, dbRef.path().toString()));

        clientRef.tell(new SetRequest("testkey", "testvalue", probe.ref()), probe.ref());

        probe.expectMsg(new Status.Success("testkey"));
        assert(db.map.get("testkey") == "testvalue");
    }

    @Test
    public void itShouldGet() throws Exception {
        TestActorRef<AkkademyDb> dbRef = TestActorRef.create(system, Props.create(AkkademyDb.class));
        AkkademyDb db = dbRef.underlyingActor();
        db.map.put("testkey", "testvalue");

        TestProbe probe = TestProbe.apply(system);
        TestActorRef<HotswapClientActor> clientRef =
                TestActorRef.create(system, Props.create(HotswapClientActor.class, dbRef.path().toString()));


        clientRef.tell(new GetRequest("testkey"), probe.ref());
        probe.expectMsg("testvalue");
    }
}
