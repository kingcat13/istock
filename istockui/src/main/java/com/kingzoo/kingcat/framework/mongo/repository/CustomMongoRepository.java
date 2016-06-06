package com.kingzoo.kingcat.framework.mongo.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by gonghongrui on 16/6/4.
 */
@NoRepositoryBean
public interface CustomMongoRepository<T, ID extends Serializable> extends MongoRepository<T, ID> {

	Page<T> find(T condition, Pageable pageable);
	List<T> find(T condition, Sort sort);
	long count(T condition);
}
