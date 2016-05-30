package com.kingzoo.kingcat.project.dataTransfer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMapping;

@ComponentScan
@Configuration
@EnableAutoConfiguration

public class Application {

	private static final Logger LOGGER = LoggerFactory.getLogger(Application.class);

	@RequestMapping("/")
	public void startTransfer() {

		LOGGER.info("start transfer");

	}

	public static void main(String[] args) {

		SpringApplication app = new SpringApplication(Application.class);

		ApplicationContext applicationContext = SpringApplication.run(Application.class, args);


	}
}