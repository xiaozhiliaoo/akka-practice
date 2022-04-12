package demo6.device;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.cluster.sharding.ClusterSharding;
import akka.cluster.sharding.ClusterShardingSettings;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.Option;
import scala.concurrent.duration.Duration;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Devices extends AbstractActor {

    private final LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    private final ActorRef deviceRegion;

    private final Random random = new Random();

    private final Integer numberOfDevices = 50;


    public enum UpdateDevice {
        INSTANCE
    }

    public Devices() {
        ActorSystem system = getContext().getSystem();
        ClusterShardingSettings settings = ClusterShardingSettings.create(system);
        deviceRegion = ClusterSharding.get(system)
                .start(
                        "Counter",
                        Props.create(Device.class),
                        settings,
                        new DeviceMessageExtractor());

        log.info("DeviceRegion is:{}", deviceRegion);

        getContext().getSystem().scheduler().schedule(
                Duration.create(10, TimeUnit.SECONDS),
                Duration.create(5, TimeUnit.SECONDS),
                getSelf(),
                UpdateDevice.INSTANCE,
                system.dispatcher(),
                null
        );
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(UpdateDevice.class, u -> {
                    Integer deviceId = random.nextInt(numberOfDevices);
                    Double temperature = 5 + 30 * random.nextDouble();
                    Device.RecordTemperature msg = new Device.RecordTemperature(deviceId, temperature);
                    log.info("Sending {}", msg);
                    deviceRegion.tell(msg, getSelf());
                })
                .build();
    }
}
