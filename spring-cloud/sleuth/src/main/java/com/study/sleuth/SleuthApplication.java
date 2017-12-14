package com.study.sleuth;

import com.p6spy.engine.common.ConnectionInformation;
import com.p6spy.engine.event.JdbcEventListener;
import net.ttddyy.dsproxy.ExecutionInfo;
import net.ttddyy.dsproxy.QueryInfo;
import net.ttddyy.dsproxy.listener.QueryExecutionListener;
import net.ttddyy.dsproxy.transform.ParameterTransformer;
import net.ttddyy.dsproxy.transform.QueryTransformer;
import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.sleuth.sampler.AlwaysSampler;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.sql.SQLException;
import java.util.List;

@EnableDiscoveryClient
@SpringBootApplication
@EnableCircuitBreaker
public class SleuthApplication {
	private final Logger logger = Logger.getLogger(SleuthApplication.class.getName());
	public static void main(String[] args) {
		SpringApplication.run(SleuthApplication.class, args);
	}


    @Bean
    public AlwaysSampler defaultSampler() {
        return new AlwaysSampler();
    }
	@Bean
    public RestTemplate restTemplate(){
	    return new RestTemplate();
    }




}
