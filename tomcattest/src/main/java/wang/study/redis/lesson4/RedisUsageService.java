package wang.study.redis.lesson4;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

@Service
public class RedisUsageService {
    @Autowired
    private RedisTemplate template;

    @Resource(name="template")
    private ValueOperations<String, String> valueOperations;
    @Resource(name="template")
    private ListOperations<String, String> listOperations;
    @Resource(name="template")
    private SetOperations<String, String> setOperations;

    @Resource(name="template")
    private HashOperations<String, String,String> hashOperations;

    @Resource(name="template")
    private ZSetOperations<String, String> zSetOperations;

    @PostConstruct
    public void run(){

        //字符串操作
        this.useString();
        //列表操作
        this.usetList();
        //操作set
        this.useSet();
        //操作hash
        this.useHash();
        //计分set
        this.useScoreSet();
    }

    public void useString(){
        valueOperations.set("value","1");
        valueOperations.increment("value",10L);
        System.out.println(valueOperations.get("value"));
    }

    public void usetList(){
        listOperations.rightPush("list2","subkey1");
        listOperations.leftPush("list2","subkey2");
        System.out.println(listOperations.range("list2",0,10));
        listOperations.leftPop("list2");
        listOperations.leftPop("list2");
    }

    public void useSet(){
        setOperations.add("set","v1","v2");
        System.out.println(setOperations.members("set"));
        setOperations.remove("set","v1","v2");
    }
    public void useScoreSet(){
        zSetOperations.add("scoreset","v2",10L);
        zSetOperations.incrementScore("scoreset","v2",4L);
        System.out.println(zSetOperations.score("scoreset","v2"));
        zSetOperations.remove("scoreset","v2");
    }
    public void useHash(){

        System.out.println(hashOperations.get("hash","v1"));
        hashOperations.put("hash","v1","10.0");
        System.out.println(hashOperations.get("hash","v1"));
        hashOperations.delete("hash","v1");
    }

}
