package clouldtest.ribbonconsumer.service;

import clouldtest.ribbonconsumer.controller.Hello;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCollapser;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheResult;
import com.netflix.hystrix.contrib.javanica.command.AsyncResult;
import com.netflix.ribbon.proxy.annotation.Hystrix;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Future;

/**
 * Created by Administrator on 2017/7/28.
 */
@Service
public class HelloService {
    @Autowired
    private RestTemplate restTemplate;

    @CacheResult(cacheKeyMethod = "createCacheKey")
    @HystrixCommand(fallbackMethod = "fallbackHello")
    public String hello(String id){
        System.out.println("not from cache ");
        return restTemplate.getForEntity("http://hello-SERVICE/hello?id="+id,String.class).getBody();
    }

    @HystrixCommand
    public Future<Hello> helloWithOutBatch(String id){
        return new AsyncResult<Hello>() {
            @Override
            public Hello invoke() {
                return restTemplate.getForEntity("http://hello-SERVICE/hello?id="+id,Hello.class).getBody();
            }
        };
    }

    @HystrixCollapser(batchMethod="getAll",collapserKey = "id",collapserProperties = {
        @HystrixProperty(name="timerDelayInMilliseconds",value="100")
    })
    public Future<Hello> helloWithBatch(String id){
        return null;
    }

    @HystrixCommand
    public List<Hello> getAll(List<String> ids){
        System.out.println("batch get all "+StringUtils.collectionToCommaDelimitedString(ids));
        Hello[] values = restTemplate.getForEntity("http://hello-SERVICE/hellos?id="+ StringUtils.collectionToCommaDelimitedString(ids),Hello[].class).getBody();
        return Arrays.asList(values);
    };

    public String createCacheKey(String id){
        return id;
    }

    public String fallbackHello(String id){
        return "hello";
    }
}
