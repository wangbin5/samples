package study.kafka.client;


import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.log4j.LogManager;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/7/27.
 */
public class ConsumerRunner {
    public static final int MAX_COUNT = 6;
    private KafkaConsumer consumer;
    private String topic = "hello";
    private Map<String,Integer> counts = new HashMap<>();

    public ConsumerRunner() {
        Map prop = createConsumerConfig();
        this.consumer = new KafkaConsumer(prop);
        this.consumer.subscribe(Arrays.asList(topic));
        for(int i=0;i<MAX_COUNT;i++){
            counts.put(i+"",0);
        }
    }

    private Map createConsumerConfig() {
        Map<String,String> props = new HashMap<>();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("group.id", "test");
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "1000");
        props.put("session.timeout.ms", "30000");
        props.put("auto.offset.reset", "earliest");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        return props;
    }


    public void receive() {
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(1000);
            for (final ConsumerRecord record : records) {
                System.out.println("Receive message: " + record.value() + ", Partition: "
                        + record.partition() + ", Offset: " + record.offset());
                int count = this.counts.get(record.partition()+"").intValue()+1;
                this.counts.put(record.partition()+"",count);
            }/*
            if(!records.isEmpty()){
                for(int i=0;i<MAX_COUNT;i++){
                    System.out.print(i+":"+this.counts.get(i+"")+"     ,");
                }
                System.out.println();
            }*/

        }
    }

    public static void main(String[] args){
        new ConsumerRunner().receive();
    }

}
