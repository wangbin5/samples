package study.kafka.client.partitioner;

import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;

import java.util.Map;
import java.util.Random;

/**
 * Created by Administrator on 2017/7/27.
 */
public class RandomPatitioner implements Partitioner{
    public static final int MAX_PARTITION_COUNT = 6;
    Random random = new Random();
    @Override
    public int partition(String s, Object o, byte[] bytes, Object o1, byte[] bytes1, Cluster cluster) {
        int code = random.nextInt(120)%MAX_PARTITION_COUNT;
        System.out.println(code);
        return code;
    }

    @Override
    public void close() {

    }

    @Override
    public void configure(Map<String, ?> map) {
    }
}
