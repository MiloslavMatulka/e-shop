package com.engeto.eshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Spring Boot web application that defines endpoints for REST API and renders
 * data on the frontend with Thymeleaf.
 *
 * @author Miloslav Matulka (Discord tag Miloslav#8572)
 */
@SpringBootApplication
public class EShopApplication {

	public static void main(String[] args) {
		SpringApplication.run(EShopApplication.class, args);
	}

}
