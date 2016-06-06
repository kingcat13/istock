package com.kingzoo.kingcat.project.istockui;

import com.kingzoo.kingcat.framework.mongo.repository.CustomMongoRepositoryFactoryBean;

import com.kingzoo.kingcat.project.istockui.core.stock.dao.IStockDao;
import com.kingzoo.kingcat.project.istockui.core.stock.domain.Stock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
