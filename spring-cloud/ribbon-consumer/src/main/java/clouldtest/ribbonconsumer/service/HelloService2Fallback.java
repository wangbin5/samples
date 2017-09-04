package clouldtest.ribbonconsumer.service;

import clouldtest.ribbonconsumer.controller.Hello;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/7/28.
 */
@Component
public class HelloService2Fallback implements HelloService2{


    @Override
    public Hello hello(@RequestParam("id") String id) {
        return new Hello();
    }

    @Override
    public List<Hello> hellos(@RequestParam("id") List<String> ids) {
        return new ArrayList<Hello>();
    }
}
