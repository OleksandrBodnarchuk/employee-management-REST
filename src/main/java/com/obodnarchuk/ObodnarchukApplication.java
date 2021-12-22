package com.obodnarchuk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
public class ObodnarchukApplication {
	public static void main(String[] args) {
		SpringApplication.run(ObodnarchukApplication.class, args);
	}

}
