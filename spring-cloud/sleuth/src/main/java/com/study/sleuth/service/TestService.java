package com.study.sleuth.service;

import com.study.sleuth.SleuthApplication;
import com.study.sleuth.model.LockTable;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

/**
 * Created by Administrator on 2017/12/14.
 */
@Service
public class TestService {
    private final Logger logger = Logger.getLogger(SleuthApplication.class.getName());
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private EntityManager entityManager;

    public String hello2(@RequestParam("id") String id){
        logger.info("hello2");
        return this.restTemplate.getForObject("http://localhost:1112/hello3?id=3",String.class);
    }

    public Long hello4(){

        return jdbcTemplate.queryForObject("select count(*) from zipkin_spans",Long.class);
    }

    public void hello5(){
        jdbcTemplate.execute("insert into test(id) values('123')");
    }

    @Transactional
    public void saveLock(){
        LockTable lockTable = new LockTable();
        lockTable.setKeytype("test1");
        lockTable.setValue("lock");
        this.entityManager.persist(lockTable);
    }
}
