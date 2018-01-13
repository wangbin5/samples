package wang.study.redis.lesson4;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisUsage {
    private JedisPool jedisPool;

    public void init(){
        this.jedisPool = this.createRedisPool();
    }

    public JedisPool createRedisPool(){
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxIdle(5);
        config.setTestOnBorrow(false);

        JedisPool jedisPool = new JedisPool(config,"192.168.231.128",6379);
        return jedisPool;
    }

    public void useString(){
        Jedis jedis = jedisPool.getResource();
        jedis.set("key","1");
        jedis.incr("key");
        System.out.println(jedis.get("key"));
        jedis.close();
    }

    public void usetList(){
        Jedis jedis = jedisPool.getResource();
        jedis.rpush("list2","subkey1");
        jedis.lpush("list2","subkey2");
        System.out.println(jedis.lrange("list2",0,10));
        jedis.lpop("list2");
        jedis.lpop("list2");
        jedis.close();
    }

    public void useSet(){
        Jedis jedis = jedisPool.getResource();
        jedis.sadd("set","v1","v2");
        System.out.println(jedis.smembers("set"));
        jedis.srem("set","v1","v2");
        jedis.close();
    }
    public void useScoreSet(){
        Jedis jedis = jedisPool.getResource();
        jedis.zadd("scoreset",10,"v2");
        jedis.zincrby("scoreset",4,"v2");
        System.out.println(jedis.zscore("scoreset","v2"));
        jedis.zrem("scoreset","v2");
        jedis.close();
    }
    public void useHash(){
        Jedis jedis = jedisPool.getResource();
        System.out.println(jedis.hget("hash","v1"));
        jedis.hset("hash","v1","v2");
        System.out.println(jedis.hget("hash","v1"));
        jedis.hdel("hash","v1");
        jedis.close();
    }


    private void close() {
        this.jedisPool.close();
    }
    public static void main(String[] args){
        RedisUsage usage = new RedisUsage();
        usage.init();
        System.out.println("init success");
        //字符串操作
        usage.useString();
        //列表操作
        usage.usetList();
        //操作set
        usage.useSet();
        //操作hash
        usage.useHash();
        //计分set
        usage.useScoreSet();
        //关闭连接池
        usage.close();


    }


}
