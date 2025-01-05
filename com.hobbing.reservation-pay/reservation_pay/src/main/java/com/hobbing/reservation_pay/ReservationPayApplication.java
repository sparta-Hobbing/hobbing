package com.hobbing.reservation_pay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class ReservationPayApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReservationPayApplication.class, args);
	}

}
