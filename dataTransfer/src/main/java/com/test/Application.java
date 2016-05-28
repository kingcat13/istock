package com.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;

@EnableAutoConfiguration
public class Application {

	private static final Logger LOGGER = LoggerFactory.getLogger(Application.class);

	@RequestMapping("/")
	public void startTransfer() {

		LOGGER.info("start transfer");

	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}