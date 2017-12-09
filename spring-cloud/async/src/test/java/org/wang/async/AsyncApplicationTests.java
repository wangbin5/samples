package org.wang.async;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.concurrent.ExecutionException;
import java.util.function.Consumer;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AsyncApplicationTests {

	@Test
	public void contextLoads() throws InterruptedException, ExecutionException {

		WebClient client = WebClient.create("http://localhost:5000");

		Mono<String> result = client.get()
				.uri("/v1.0/organizations").accept(MediaType.APPLICATION_JSON)
				.retrieve()
				.bodyToMono(String.class);
		result.doOnSuccess(new Consumer<String>() {
			@Override
			public void accept(String s) {
				System.out.println("result "+s);
			}
		});

		Thread.sleep(10000);

	}

}
