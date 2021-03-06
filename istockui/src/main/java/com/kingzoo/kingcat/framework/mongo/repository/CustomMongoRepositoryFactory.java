/*
 * Copyright 2010-2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.kingzoo.kingcat.framework.mongo.repository;

import static org.springframework.data.querydsl.QueryDslUtils.*;

import java.io.Serializable;
import java.lang.reflect.Method;

import org.springframework.data.mapping.context.MappingContext;
import org.springframework.data.mapping.model.MappingException;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.mapping.MongoPersistentEntity;
import org.springframework.data.mongodb.core.mapping.MongoPersistentProperty;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.query.MongoEntityInformation;
import org.springframework.data.mongodb.repository.query.MongoQueryMethod;
import org.springframework.data.mongodb.repository.query.PartTreeMongoQuery;
import org.springframework.data.mongodb.repository.query.StringBasedMongoQuery;
import org.springframework.data.mongodb.repository.support.MappingMongoEntityInformation;
import org.springframework.data.mongodb.repository.support.QueryDslMongoRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.core.NamedQueries;
import org.springframework.data.repository.core.RepositoryInformation;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;
import org.springframework.data.repository.query.EvaluationContextProvider;
import org.springframework.data.repository.query.QueryLookupStrategy;
import org.springframework.data.repository.query.QueryLookupStrategy.Key;
import org.springframework.data.repository.query.RepositoryQuery;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.util.Assert;

/**
 * Factory to create {@link MongoRepository} instances.
 * 
 * @author Oliver Gierke
 * @author Thomas Darimont
 * @author Christoph Strobl
 */
public class CustomMongoRepositoryFactory extends RepositoryFactorySupport {

	private static final SpelExpressionParser EXPRESSION_PARSER = new SpelExpressionParser();

	private final MongoOperations mongoOperations;
	private final MappingContext<? extends MongoPersistentEntity<?>, MongoPersistentProperty> mappingContext;

	/**
	 * Creates a new {@link CustomMongoRepositoryFactory} with the given {@link MongoOperations}.
	 * 
	 * @param mongoOperations must not be {@literal null}.
	 */
	public CustomMongoRepositoryFactory(MongoOperations mongoOperations) {

		Assert.notNull(mongoOperations);

		this.mongoOperations = mongoOperations;
		this.mappingContext = mongoOperations.getConverter().getMappingContext();
	}

	/*
	 * (non-Javadoc)
	 * @see org.springframework.data.repository.core.support.RepositoryFactorySupport#getRepositoryBaseClass(org.springframework.data.repository.core.RepositoryMetadata)
	 */
	@Override
	protected Class<?> getRepositoryBaseClass(RepositoryMetadata metadata) {

		boolean isQueryDslRepository = QUERY_DSL_PRESENT
				&& QueryDslPredicateExecutor.class.isAssignableFrom(metadata.getRepositoryInterface());

//		return isQueryDslRepository ? QueryDslMongoRepository.class : SimpleMongoRepository.class;
		return isQueryDslRepository ? QueryDslMongoRepository.class : CustomMongoRepositoryImpl.class;
	}

	/*
	 * (non-Javadoc)
	 * @see org.springframework.data.repository.core.support.RepositoryFactorySupport#getTargetRepository(org.springframework.data.repository.core.RepositoryInformation)
	 */
	@Override
	protected Object getTargetRepository(RepositoryInformation information) {

		MongoEntityInformation<?, Serializable> entityInformation = getEntityInformation(information.getDomainType(),
				information);
		//return getTargetRepositoryViaReflection(information, entityInformation, mongoOperations);
		return getTargetRepositoryViaReflection(information, entityInformation, mongoOperations);
	}

	/* 
	 * (non-Javadoc)
	 * @see org.springframework.data.repository.core.support.RepositoryFactorySupport#getQueryLookupStrategy(org.springframework.data.repository.query.QueryLookupStrategy.Key, org.springframework.data.repository.query.EvaluationContextProvider)
	 */
	@Override
	protected QueryLookupStrategy getQueryLookupStrategy(Key key, EvaluationContextProvider evaluationContextProvider) {
		return new MongoQueryLookupStrategy(evaluationContextProvider);
	}

	/*
	 * (non-Javadoc)
	 * @see org.springframework.data.repository.core.support.RepositoryFactorySupport#getEntityInformation(java.lang.Class)
	 */
	public <T, ID extends Serializable> MongoEntityInformation<T, ID> getEntityInformation(Class<T> domainClass) {
		return getEntityInformation(domainClass, null);
	}

	@SuppressWarnings("unchecked")
	private <T, ID extends Serializable> MongoEntityInformation<T, ID> getEntityInformation(Class<T> domainClass,
			RepositoryInformation information) {

		MongoPersistentEntity<?> entity = mappingContext.getPersistentEntity(domainClass);

		if (entity == null) {
			throw new MappingException(
					String.format("Could not lookup mapping metadata for domain class %s!", domainClass.getName()));
		}

		return new MappingMongoEntityInformation<T, ID>((MongoPersistentEntity<T>) entity,
				information != null ? (Class<ID>) information.getIdType() : null);
	}

	/**
	 * {@link QueryLookupStrategy} to create {@link PartTreeMongoQuery} instances.
	 * 
	 * @author Oliver Gierke
	 * @author Thomas Darimont
	 */
	private class MongoQueryLookupStrategy implements QueryLookupStrategy {

		private final EvaluationContextProvider evaluationContextProvider;

		public MongoQueryLookupStrategy(EvaluationContextProvider evaluationContextProvider) {
			this.evaluationContextProvider = evaluationContextProvider;
		}

		/*
		 * (non-Javadoc)
		 * @see org.springframework.data.repository.query.QueryLookupStrategy#resolveQuery(java.lang.reflect.Method, org.springframework.data.repository.core.RepositoryMetadata, org.springframework.data.repository.core.NamedQueries)
		 */
		public RepositoryQuery resolveQuery(Method method, RepositoryMetadata metadata, NamedQueries namedQueries) {

			MongoQueryMethod queryMethod = new MongoQueryMethod(method, metadata, mappingContext);
			String namedQueryName = queryMethod.getNamedQueryName();

			if (namedQueries.hasQuery(namedQueryName)) {
				String namedQuery = namedQueries.getQuery(namedQueryName);
				return new StringBasedMongoQuery(namedQuery, queryMethod, mongoOperations, EXPRESSION_PARSER,
						evaluationContextProvider);
			} else if (queryMethod.hasAnnotatedQuery()) {
				return new StringBasedMongoQuery(queryMethod, mongoOperations, EXPRESSION_PARSER, evaluationContextProvider);
			} else {
				return new PartTreeMongoQuery(queryMethod, mongoOperations);
			}
		}
	}
}
