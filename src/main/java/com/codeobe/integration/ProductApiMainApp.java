package com.codeobe.integration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@ComponentScan(basePackages = {"org.codeobe.integration", "com.codeobe.integration"})
public class ProductApiMainApp  {

	private static final Logger log = LogManager.getLogger(ProductApiMainApp.class);

	public static void main(String[] args) {
		SpringApplication.run(ProductApiMainApp.class, args);
		log.info("Ayubowan... Starting SpringApplication.run(MyMainClass.class, args) " );
	}
}
