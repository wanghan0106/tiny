package com.roy.tiny.base.dao;

import java.util.HashMap;
import java.util.Map;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.roy.tiny.base.dao.impl.BaseDAOImpl;

@Repository("daoFactory")
public class DaoFactory {
	@Autowired
	protected SessionFactory sessionFactory;
	
	private Map<Class,BaseDAOImpl> daoCache = new HashMap<Class,BaseDAOImpl>();

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public BaseDAO getDao(Class entityClass) {
		BaseDAOImpl dao = null;
		synchronized (daoCache) {
			dao = daoCache.get(entityClass);
			if(dao == null) {
				dao = new BaseDAOImpl();
				dao.setEntityClass(entityClass);
				dao.setSessionFactory(sessionFactory);
				daoCache.put(entityClass, dao);
			}
		}
		return dao;
	}
}
