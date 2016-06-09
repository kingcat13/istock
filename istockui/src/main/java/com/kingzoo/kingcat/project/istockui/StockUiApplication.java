package com.kingzoo.kingcat.project.istockui;

import com.kingzoo.kingcat.framework.mongo.repository.CustomMongoRepositoryFactoryBean;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@SpringBootApplication
@EnableMongoRepositories(repositoryFactoryBeanClass = CustomMongoRepositoryFactoryBean.class)
@Controller
public class StockUiApplication {




	@RequestMapping(value = "/{page}")
	public String toPage(@PathVariable String page){
		return page;

	}

	public static void main(String[] args) {
		SpringApplication.run(StockUiApplication.class, args);
	}
}
