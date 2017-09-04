package study.kafka.client.partitioner;

import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;

import java.util.Map;
import java.util.Random;

/**
 * Created by Administrator on 2017/7/27.
 */
public class CountPartitioner  implements Partitioner {
    public static final int MAX_PARTITION_COUNT = 6;
    int count = 0;
    int randomcount = 0;
    public CountPartitioner(){
        this.randomcount = new Random().nextInt(100);
    }
    @Override
    public int partition(String s, Object o, byte[] bytes, Object o1, byte[] bytes1, Cluster cluster) {
        count++;
        System.out.println(randomcount+ " count is "+count+","+new String(bytes));
        return count%MAX_PARTITION_COUNT;
    }

    @Override
    public void close() {

    }

    @Override
    public void configure(Map<String, ?> map) {

    }
}
