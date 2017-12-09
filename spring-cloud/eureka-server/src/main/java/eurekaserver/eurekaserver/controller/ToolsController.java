package eurekaserver.eurekaserver.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 配合测试使用
 */
@RestController
public class ToolsController {
    private AtomicInteger count = new AtomicInteger(0);
    @RequestMapping(value = "/timeout", method = RequestMethod.GET)
    public String timeout(){
        int c = count.addAndGet(1);
        System.out.println("c is "+c);
        //响应时间在100s
        try {
            int sec = c%30 == 0 ? 20 : 0;
            if(sec == 20){
                Thread.sleep(sec*1000);
                return "sleep";
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        throw new RuntimeException("error ...");
    }

}
