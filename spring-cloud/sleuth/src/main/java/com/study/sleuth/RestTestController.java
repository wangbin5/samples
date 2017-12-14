package com.study.sleuth;

import com.study.sleuth.model.LockTable;
import com.study.sleuth.service.TestService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

/**
 * Created by Administrator on 2017/12/8.
 */

@RestController
public class RestTestController {
    private final Logger logger = Logger.getLogger(SleuthApplication.class.getName());

    @Autowired
    private TestService testService;



    @RequestMapping("/hello")
    public String hello(@RequestParam("id") String id){
        logger.info("hello");
        return "hello "+id;
    }
    @RequestMapping("/hello2")
    public String hello2(@RequestParam("id") String id){
        return testService.hello2(id);
    }

    @RequestMapping("/hello4")
    public Long hello4(){
        return testService.hello4();
    }

    @RequestMapping("/hello5")
    public void hello5(){
         testService.hello5();
    }

    @RequestMapping("/hello6")
    @Transactional
    public void saveLock(){
        testService.saveLock();
    }


}
