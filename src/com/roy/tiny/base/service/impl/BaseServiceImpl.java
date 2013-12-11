package com.roy.tiny.base.service.impl;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.roy.tiny.base.dao.BaseDAO;
import com.roy.tiny.base.dao.DaoFactory;
import com.roy.tiny.base.dao.cond.Cond;
import com.roy.tiny.base.model.Model;
import com.roy.tiny.base.service.BaseService;
import com.roy.tiny.base.service.annotation.Dao;
import com.roy.tiny.base.web.Pager;
import com.roy.tiny.base.web.Sorter;
import com.roy.tiny.topic.service.impl.TopicServiceImpl;

public abstract class BaseServiceImpl<T extends Model> implements BaseService<T> {
	
	private static final Logger log = LoggerFactory.getLogger(BaseServiceImpl.class);
	
	@Autowired
	private DaoFactory daoFactory;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected BaseDAO<T> getDao() {
		java.lang.reflect.Type type = this.getClass().getGenericSuperclass();
		ParameterizedType ptype = (ParameterizedType) type;
		Class entityClass = (Class<T>) ptype.getActualTypeArguments()[0];
		return daoFactory.getDao(entityClass);
	}

	@Override
	public void save(T object) {
		getDao().save(object);
	}

	@Override
	public void delete(T object) {
		getDao().delete(object);
	}

	@Override
	public T get(Serializable id) {
		return getDao().get(id);
	}

	@Override
	public List<T> query(Cond cond, Pager pager, Sorter sorter) {
		return getDao().query(cond, pager, sorter);
	}

	@Override
	public List<T> query(Cond cond, Sorter sorter) {
		return getDao().query(cond, sorter);
	}
	
	@Override
	public List<T> query(Cond cond) {
		return getDao().query(cond);
	}
	
	@Override
	public T get(Cond cond) {
		return getDao().get(cond);
	}

}
