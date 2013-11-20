package com.roy.tiny.base.dao;

import java.io.Serializable;
import java.util.List;

import com.roy.tiny.base.dao.cond.Cond;
import com.roy.tiny.base.model.Model;
import com.roy.tiny.base.web.Pager;
import com.roy.tiny.base.web.Sorter;

public interface BaseDAO<T extends Model> {
	public void save(T object);
	public void delete(T object);
	public T get(Serializable id);
	public List<T> query(Cond cond);
	public List<T> query(Cond cond,Sorter sorter);
	public List<T> query(Cond cond,Pager pager,Sorter sorter);
	
	/**
	 * Be careful! Make sure that searching by this condition will return only one result.
	 * @param cond query condition
	 * @return an unique result
	 */
	public T get(Cond cond);
	
}
