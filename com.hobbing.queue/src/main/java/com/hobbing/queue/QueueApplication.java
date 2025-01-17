package com.hobbing.queue;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;


@EnableCaching
@SpringBootApplication
public class QueueApplication {

	public static void main(String[] args) {
		SpringApplication.run(QueueApplication.class, args);
	}

}
