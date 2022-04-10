package ch8.shard;

import akka.cluster.sharding.ShardRegion;

/**
 * @author lili
 * @date 2022/4/10 23:18
 */
public class UserMessageExtractor implements ShardRegion.MessageExtractor {
    @Override
    public String entityId(Object message) {
        String entityId = null;
        if (message instanceof Cmd) {
            Cmd cmd = (Cmd) message;
            entityId = cmd.getUserId() + "";
        }
        return entityId;
    }

    @Override
    public Object entityMessage(Object message) {
        return message;
    }

    @Override
    public String shardId(Object message) {
        String shardId = null;
        int numberOfShards = 10;
        if (message instanceof Cmd) {
            Cmd cmd = (Cmd) message;
            shardId = (cmd.getUserId() % numberOfShards) + "";
        }
        return shardId;
    }
}
