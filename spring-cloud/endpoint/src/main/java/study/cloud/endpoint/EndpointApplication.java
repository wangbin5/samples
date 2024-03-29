package study.cloud.endpoint;

import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.sleuth.sampler.AlwaysSampler;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@EnableDiscoveryClient
@EnableCircuitBreaker
@SpringBootApplication
@RestController
public class EndpointApplication {
	private final Logger logger = Logger.getLogger(EndpointApplication.class.getName());
	public static void main(String[] args) {
		SpringApplication.run(EndpointApplication.class, args);
	}


    @Bean
    public AlwaysSampler defaultSampler() {
        return new AlwaysSampler();
    }
}
