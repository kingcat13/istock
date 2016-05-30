package com.kingzoo.kingcat.project.dataTransfer2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class DataTransfer2Application {

	@RequestMapping(value ="/")
	String index(){
		return "你好";
	}

	public static void main(String[] args) {
		SpringApplication.run(DataTransfer2Application.class, args);
	}
}
