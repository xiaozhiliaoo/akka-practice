package demo6.book;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.japi.Procedure;
import akka.persistence.AbstractPersistentActor;
import akka.persistence.RecoveryCompleted;
import akka.persistence.SaveSnapshotSuccess;
import akka.persistence.SnapshotOffer;
import com.typesafe.config.ConfigFactory;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Data
class Action implements Serializable {
    private String cmd;
    private String data;

    @Override
    public String toString() {
        return this.cmd + "------>" + this.data;
    }
}

/**
 * 处理用户持久化信息的actor
 */
public class UserService extends AbstractPersistentActor {
    private List<Action> states = new ArrayList<>();

    @Override
    public String persistenceId() {
        return "userservice-1";
    }


    @Override
    public Receive createReceiveRecover() {

        return receiveBuilder()
                .match(Action.class, msg -> {
                    Action evt = (Action) msg;
                    states.add(evt);
                })
                .match(SnapshotOffer.class, msg -> {
                    SnapshotOffer snapshotOffer = (SnapshotOffer) msg;
                    states = (List<Action>) snapshotOffer.snapshot();
                    System.out.println("recover: " + states);
                })
                .match(RecoveryCompleted.class, msg -> {
                    System.out.println("replay has been finished");
                })
                .matchAny(msg -> {
                    System.out.println("onReceiveRecover: " + msg + ", " + msg.getClass());
                })
                .build();
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(Action.class, msg -> {
                    Action action = (Action) msg;
                    if (action.getCmd().equals("save")) {
                        //持久化事件
                        persist(action, new Procedure<Action>() {
                            @Override
                            public void apply(Action act) throws Exception {
                                states.add(act);
                            }
                        });
                    } else if (action.getCmd().equals("saveAll")) {
                        //保存快照
                        saveSnapshot(states);
                    } else if (action.getCmd().equals("get")) {
                        System.out.println("state: " + states);
                    }
                })
                .match(SaveSnapshotSuccess.class, msg -> {
                    SaveSnapshotSuccess saveSnapshotSuccess = (SaveSnapshotSuccess) msg;
                    System.out.println("Save snap success: " + saveSnapshotSuccess.metadata());
                })
                .matchAny(msg -> {
                    System.out.println("other message " + msg);

                })
                .build();
    }


    public static void main(String[] args) {
        ActorSystem system = ActorSystem.create("sys", ConfigFactory.load("blank.conf"));
        ActorRef ref = system.actorOf(Props.create(UserService.class), "userService");
        ref.tell(new Action("save", "00000"), ActorRef.noSender());
        ref.tell(new Action("get", "9999"), ActorRef.noSender());
        ref.tell(new Action("save", "00001"), ActorRef.noSender());
        ref.tell(new Action("get", "77777"), ActorRef.noSender());
        ref.tell(new Action("saveAll", "77777"), ActorRef.noSender());
    }
}
