package com.authorservice;

import com.authorservice.service.AuthorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableEurekaClient
@RefreshScope
@EnableCaching
public class AuthorserviceApplication {
	private static final Logger logger = LoggerFactory.getLogger(SpringBootApplication.class);
	@Autowired
	private AuthorService authorService;
	public static void main(String[] args) {
		SpringApplication.run(AuthorserviceApplication.class, args);
	}
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
	public void run(String... args) throws Exception {
		logger.info("------------------ demo @Cacheable --------------------");
		for(int i = 0;i < 20;i++){
			logger.info("Find author with id = 1: {}", authorService.getAuthorWithBook(1L));
		}

	}
}
