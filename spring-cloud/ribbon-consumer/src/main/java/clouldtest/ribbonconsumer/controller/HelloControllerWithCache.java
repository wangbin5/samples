package clouldtest.ribbonconsumer.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheResult;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.ExecutionException;

/**
 * Created by Administrator on 2017/10/9.
 */
@RestController
public class HelloControllerWithCache {
    @Autowired
    private RestTemplate restTemplate;

    @CacheResult(cacheKeyMethod = "createCacheKey")
    @HystrixCommand(fallbackMethod = "fallbackHello")
    @RequestMapping(value="/hello")
    public Hello hello(@RequestParam String id) throws ExecutionException, InterruptedException {
        return restTemplate.getForEntity("http://hello-SERVICE/hello?id="+id,Hello.class).getBody();
    }


    public Hello fallbackHello(String id){
        return new Hello();
    }

}
