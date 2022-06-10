package com.emlakjet.testcontainers;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class TestcontainersDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(TestcontainersDemoApplication.class, args);
	}

}
