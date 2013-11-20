package com.roy.tiny.base.service;

import java.io.Serializable;
import java.util.List;

import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.roy.tiny.base.dao.cond.Cond;
import com.roy.tiny.base.model.Model;
import com.roy.tiny.base.web.Pager;
import com.roy.tiny.base.web.Sorter;

@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
public interface BaseService<T extends Model> {
	
	public void save(T object);
	
	public void delete(T object);
	
	@Transactional(readOnly=true)
	public T get(Serializable id);
	
	@Transactional(readOnly=true)
	public List<T> query(Cond cond,Pager pager,Sorter sorter);
	
	@Transactional(readOnly=true)
	public List<T> query(Cond cond,Sorter sorter);
	
	@Transactional(readOnly=true)
	public List<T> query(Cond cond);
	
	@Transactional(readOnly=true)
	public T get(Cond cond);
}
