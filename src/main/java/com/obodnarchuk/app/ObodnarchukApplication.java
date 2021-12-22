package com.obodnarchuk.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
@EntityScan(basePackages = {"com.obodnarchuk.model"})  // scan JPA entities
public class ObodnarchukApplication {

	public static void main(String[] args) {
		SpringApplication.run(ObodnarchukApplication.class, args);
	}

}
