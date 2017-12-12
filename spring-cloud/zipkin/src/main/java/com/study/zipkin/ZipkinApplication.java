package com.study.zipkin;

import com.github.kristofa.brave.Brave;
import com.github.kristofa.brave.mysql.MySQLStatementInterceptorManagementBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import zipkin.server.EnableZipkinServer;

@SpringBootApplication
@EnableZipkinServer
public class ZipkinApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZipkinApplication.class, args);
	}
	/*
	@Bean
	public MySQLStatementInterceptorManagementBean mySQLStatementInterceptorManagementBean(Brave brave) {
		return new MySQLStatementInterceptorManagementBean(brave.clientTracer());
	}*/
}
