package clouldtest.ribbonconsumer.controller;

import clouldtest.ribbonconsumer.service.HelloService;
import clouldtest.ribbonconsumer.service.HelloService2;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.ExecutionException;

/**
 * Created by Administrator on 2017/7/26.
 */
@RestController
public class HelloController {
    public HelloController(){

    }
    @Autowired
    private HelloService helloService;
    @Autowired
    private HelloService2 helloService2;


    @RequestMapping(value="/hello")
    public Hello hello() throws ExecutionException, InterruptedException {
        HystrixRequestContext context = HystrixRequestContext.initializeContext();
        for(int i=0;i<3;i++){
            helloService.helloWithBatch(i+"");
        }
        helloService.helloWithOutBatch("9");
        //helloService.hello("8");
        return helloService.helloWithBatch("6").get();
    }

    @RequestMapping(value="/hello2")
    public Hello hello2() throws ExecutionException, InterruptedException {
        System.out.println(helloService2.getClass());
        return helloService2.hello("123");
    }

}
