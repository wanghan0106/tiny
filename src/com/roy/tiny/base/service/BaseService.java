package com.roy.tiny.base.service;

import java.io.Serializable;
import java.util.List;

import com.roy.tiny.base.dao.cond.Cond;
import com.roy.tiny.base.model.Model;
import com.roy.tiny.base.web.Pager;
import com.roy.tiny.base.web.Sorter;

public interface BaseService<T extends Model> {
	
	public void save(T object);
	
	public void delete(T object);
	
	public T get(Serializable id);
	
	public List<T> query(Cond cond,Pager pager,Sorter sorter);
	
	public List<T> query(Cond cond,Sorter sorter);
	
	public List<T> query(Cond cond);
	
	public T get(Cond cond);
}
