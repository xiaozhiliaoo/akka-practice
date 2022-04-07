package org.lili.akka.part2;

import akka.actor.UntypedActor;
import akka.dispatch.RequiresMessageQueue;
import akka.dispatch.UnboundedControlAwareMessageQueueSemantics;

public class CaActor extends UntypedActor implements RequiresMessageQueue<UnboundedControlAwareMessageQueueSemantics> {
    @Override
    public void onReceive(Object message) throws Exception, Exception {
        System.out.println(message);
    }
}
