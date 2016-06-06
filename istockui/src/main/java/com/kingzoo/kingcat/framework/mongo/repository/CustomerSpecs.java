package com.kingzoo.kingcat.framework.mongo.repository;




import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;

/**
 * Created by gonghongrui on 16/6/3.
 */
public class CustomerSpecs {

	public static <T> Query buildQuery(T example){

		Query query = new Query();

		if(example ==null){
			return query;
		}

		final Class<T> type = (Class<T>)example.getClass();
		Field[] a = type.getDeclaredFields();

		for(Field field : a){

			Object attrValue = null;
			if(field.isAccessible()){//如果是public 方法,则直接读取值
				attrValue = ReflectionUtils.getField(field, example);
			}else{
				//如果不是public, 则将属性改成可访问,然后再直接读取值
				//TODO 如果不是public, 则通过get或者is方法获取值
				field.setAccessible(true);
				attrValue = ReflectionUtils.getField(field, example);
			}

			if(attrValue!=null){
				if(attrValue.getClass() == String.class){
					if(!StringUtils.isEmpty(attrValue)){
						query.addCriteria(Criteria.where(field.getName()).regex(attrValue.toString()));
					}
				}else{
					query.addCriteria(Criteria.where(field.getName()).is(attrValue.toString()));
				}
			}
		}

		return query;
	}


}
