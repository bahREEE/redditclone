package com.example.reddit;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class RedditApplication implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(RedditApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
	
	}

}
