package com.roy.tiny.base.service.impl;

import java.io.Serializable;
import java.util.List;

import com.roy.tiny.base.dao.BaseDAO;
import com.roy.tiny.base.dao.cond.Cond;
import com.roy.tiny.base.model.Model;
import com.roy.tiny.base.service.BaseService;
import com.roy.tiny.base.web.Pager;
import com.roy.tiny.base.web.Sorter;

public abstract class BaseServiceImpl<T extends Model> implements BaseService<T> {
	
	protected abstract BaseDAO<T> getDao();
	
	@Override
	public void save(T object) {
		getDao().save(object);
	}

	@Override
	public void update(T object) {
		getDao().update(object);
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
	public List query(Cond cond, Pager pager, Sorter sorter) {
		return getDao().query(cond, pager, sorter);
	}

	@Override
	public List query(Cond cond, Sorter sorter) {
		return getDao().query(cond, sorter);
	}
	
	@Override
	public List query(Cond cond) {
		return getDao().query(cond);
	}
	
	@Override
	public T get(Cond cond) {
		return getDao().get(cond);
	}

}
