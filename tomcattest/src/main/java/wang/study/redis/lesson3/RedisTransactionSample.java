package wang.study.redis.lesson3;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Pipeline;

public class RedisTransactionSample {
    private Jedis jedis;//非切片额客户端连接


    public RedisTransactionSample(){
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxIdle(5);
        config.setTestOnBorrow(false);

        JedisPool jedisPool = new JedisPool(config,"192.168.231.128",6379);
        jedis = jedisPool.getResource();

    }

    public static void main(String[] args){
        new RedisTransactionSample().run();
        System.out.println("exit ");
    }

    private void run() {
        jedis.set("key1","123");
        System.out.println(jedis.get("key1"));
        Pipeline pipeline = jedis.pipelined();
        int i = 0;
        while(i<5){
            try{
                pipeline.watch("key2");
                if(!pipeline.sismember("key2","a").get()){
                    jedis.unwatch();
                }
                pipeline.multi();
                pipeline.zadd("key2",10,"a");
                pipeline.zadd("key3",10,"b");
                pipeline.exec();
                System.out.println("execute pipe ");
                return;
            }
            catch(Exception ex){
                ex.printStackTrace();
                //继续尝试
                i++;
            }
            System.out.println(i);
        }
        System.out.println("run heere");
    }
}
