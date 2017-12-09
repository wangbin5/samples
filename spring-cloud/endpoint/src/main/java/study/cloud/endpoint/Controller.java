package study.cloud.endpoint;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Administrator on 2017/6/28.
 */

@RestController
public class Controller {

    private final Logger logger = Logger.getLogger(Controller.class.getName());


    @Autowired
    private DiscoveryClient client;

    @RequestMapping("/hello")
    public Hello hello(@RequestParam("id") String id){
        try {
            Thread.sleep(new Random().nextInt(3000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ServiceInstance instance = client.getLocalServiceInstance();
        String message  = "/hello,host:"+instance.getHost()+",service_id : "+instance.getServiceId()+",port: "+instance.getPort();
        logger.log(Level.INFO, "you called home");
        Hello hello = new Hello();
        hello.setId(id);
        hello.setMessage(message);
        return hello;
    }

    @RequestMapping("/hellos")
    public List<Hello> hellos(@RequestParam("id") List<String> ids){
        ServiceInstance instance = client.getLocalServiceInstance();
        String message  = "/hellos,host:"+instance.getHost()+",service_id : "+instance.getServiceId()+",port: "+instance.getPort();
        logger.info(message);
        List<Hello> items = new ArrayList<>();
        for(String id : ids){
            Hello hello = new Hello();
            hello.setId(id);
            hello.setMessage(message);
            items.add(hello);
        }
        return items;
    }

    @RequestMapping("/hello3")
    public String hello3(@RequestParam("id") String id){
        logger.info("hello");
        return "hello "+id;
    }
}
