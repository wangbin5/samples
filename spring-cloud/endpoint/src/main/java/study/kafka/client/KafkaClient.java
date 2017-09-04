package study.kafka.client;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.*;

/**
 * Created by Administrator on 2017/7/27.
 */
public class KafkaClient {
    Producer<String,String> producer;

    public KafkaClient(){
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("acks", "all");
        props.put("retries", 0);
        props.put("batch.size", 16384);
        props.put("linger.ms", 1);
        props.put("buffer.memory", 33554432);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("partitioner.class","study.kafka.client.partitioner.CountPartitioner");

        producer = new KafkaProducer<>(props);
    }

    public void produce(){
        for(int i=0;i<1000;i++){
            String text = Integer.toString(i);
            System.out.println("send message " + text);
            producer.send(new ProducerRecord<String, String>("hello",text,text));
        }
        producer.flush();
    }

    class Run implements Runnable{
        private String name;
        public Run(String name){
            this.name = name;
        }
        @Override
        public void run() {
            for(int i=0;i<100;i++){
                String text = name+" "+Integer.toString(i);
                producer.send(new ProducerRecord<String, String>("hello",text,text));
            }
        }
    }

    public void multi_thread_produce(){
        List<Thread> threads = new ArrayList<>();
        for(int i=0;i<10;i++){
            Thread thread = new Thread(new Run("thread "+i) );
            thread.start();
            threads.add(thread);
        }
        for(Thread t : threads){
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args){
        KafkaClient client = new KafkaClient();
        client.multi_thread_produce();
        client.producer.close();
    }

}
