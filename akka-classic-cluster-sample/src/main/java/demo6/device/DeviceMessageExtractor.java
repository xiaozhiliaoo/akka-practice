package demo6.device;

import akka.cluster.sharding.ShardRegion;

/**
 * @author lili
 * @date 2022/4/12 21:02
 */
public class DeviceMessageExtractor implements ShardRegion.MessageExtractor {
    @Override
    public String entityId(Object message) {
        if (message instanceof Device.RecordTemperature) {
            return String.valueOf(((Device.RecordTemperature) message).deviceId);
        } else {
            return null;
        }
    }

    @Override
    public Object entityMessage(Object message) {
        if (message instanceof Device.RecordTemperature)
            return message;
        else
            return message;
    }

    @Override
    public String shardId(Object message) {
        int numberOfShards = 100;
        if (message instanceof Device.RecordTemperature) {
            //id=[0,50]
            long id = ((Device.RecordTemperature) message).deviceId;
            return String.valueOf(id % numberOfShards);
            // Needed if you want to use 'remember entities':
            // } else if (message instanceof ShardRegion.StartEntity) {
            //   long id = ((ShardRegion.StartEntity) message).id;
            //   return String.valueOf(id % numberOfShards)
        } else {
            return null;
        }
    }
}
