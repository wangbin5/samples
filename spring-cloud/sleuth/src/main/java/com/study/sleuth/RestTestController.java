package com.study.sleuth;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * Created by Administrator on 2017/12/8.
 */

@RestController
public class RestTestController {
    private final Logger logger = Logger.getLogger(SleuthApplication.class.getName());
    @Autowired
    private RestTemplate restTemplate;
    @RequestMapping("/hello")
    public String hello(@RequestParam("id") String id){
        logger.info("hello");
        return "hello "+id;
    }
    @RequestMapping("/hello2")
    public String hello2(@RequestParam("id") String id){
        logger.info("hello2");
        return this.restTemplate.getForObject("http://localhost:1112/hello3?id=3",String.class);
    }
}
