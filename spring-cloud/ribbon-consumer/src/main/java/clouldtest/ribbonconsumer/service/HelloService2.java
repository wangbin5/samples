package clouldtest.ribbonconsumer.service;

import clouldtest.ribbonconsumer.controller.Hello;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Created by Administrator on 2017/7/28.
 */
@FeignClient(name="hello-SERVICE",fallback = HelloService2Fallback.class)
public interface HelloService2 {
    @RequestMapping(value="/hello",method= RequestMethod.GET)
    public Hello hello(@RequestParam("id") String id);

    @RequestMapping(value="/hellos",method= RequestMethod.GET)
    public List<Hello> hellos(@RequestParam("id") List<String> ids);


}
