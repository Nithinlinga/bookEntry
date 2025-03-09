package com.newapp.bookEntry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication(scanBasePackages = "com.newapp")
@EnableTransactionManagement
@EnableScheduling
@RestController
public class BookEntryApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookEntryApplication.class, args);
	}

	@Bean
	public PlatformTransactionManager addTransactionBean(MongoDatabaseFactory dbFactory){
		return new MongoTransactionManager(dbFactory);
	}
	@Bean
	public RestTemplate restTemplate(){
		return new RestTemplate();
	}

	@GetMapping
	public String getName(){
		return "This is my New Springboot server";
	}

}
