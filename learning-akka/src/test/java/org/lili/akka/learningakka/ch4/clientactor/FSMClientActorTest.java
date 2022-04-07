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

public class FSMClientActorTest {
    ActorSystem system = ActorSystem.create("testSystem", ConfigFactory.defaultReference());
    Timeout timeout = new Timeout(10000, TimeUnit.MILLISECONDS);

    TestActorRef<AkkademyDb> dbRef = TestActorRef.create(system, Props.create(AkkademyDb.class));
    AkkademyDb db = dbRef.underlyingActor();

    TestProbe probe = TestProbe.apply(system);

    @Test
    public void itShouldTransitionToConnectedAndPending() throws Exception {
        TestActorRef<FSMClientActor> fsmClientRef =
                TestActorRef.create(system, Props.create(FSMClientActor.class, dbRef.path().toString()));

        assert (fsmClientRef.underlyingActor().stateName() == State.DISCONNECTED);
        fsmClientRef.tell(new GetRequest("testkey"), probe.ref());

        assert (fsmClientRef.underlyingActor().stateName() == State.CONNECTED_AND_PENDING);
    }

    @Test
    public void itShouldFlushMessagesInConnectedAndPending() throws Exception {
        TestActorRef<FSMClientActor> fsmClientRef =
                TestActorRef.create(system, Props.create(FSMClientActor.class, dbRef.path().toString()));

        fsmClientRef.tell(new SetRequest("testkey", "testvalue", probe.ref()), probe.ref());

        assert (fsmClientRef.underlyingActor().stateName() == State.CONNECTED_AND_PENDING);

        fsmClientRef.tell(new FlushMsg(), probe.ref());

        probe.expectMsgClass(Status.Success.class);
        assert (fsmClientRef.underlyingActor().stateName() == State.CONNECTED);
    }

}
