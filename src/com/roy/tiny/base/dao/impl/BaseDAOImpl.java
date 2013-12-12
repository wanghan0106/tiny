package com.roy.tiny.base.dao.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.FlushMode;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.sql.JoinType;
import org.hibernate.type.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.roy.tiny.base.dao.AliasHelper;
import com.roy.tiny.base.dao.BaseDAO;
import com.roy.tiny.base.dao.cond.Cond;
import com.roy.tiny.base.model.Model;
import com.roy.tiny.base.web.Pager;
import com.roy.tiny.base.web.Sort;
import com.roy.tiny.base.web.Sorter;

public class BaseDAOImpl<T extends Model> implements BaseDAO<T> {

	private static final Logger log = LoggerFactory
			.getLogger(BaseDAOImpl.class);

	@Autowired
	protected SessionFactory sessionFactory;
	
	private Class<T> entityClass;

	public void setEntityClass(Class<T> entityClass) {
		this.entityClass = entityClass;
	}
	
	public Class<T> getEntityClass() {
		if(entityClass == null) {
			java.lang.reflect.Type type = this.getClass().getGenericSuperclass();
			if(type instanceof ParameterizedType) {
				ParameterizedType ptype = (ParameterizedType) type;
				entityClass = (Class<T>) ptype.getActualTypeArguments()[0];
			}
		}
		return entityClass;
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public void save(T object) {
		sessionFactory.getCurrentSession().saveOrUpdate(object);
		sessionFactory.getCurrentSession().flush();
	}

	@Override
	public void delete(T object) {
		sessionFactory.getCurrentSession().delete(object);
		sessionFactory.getCurrentSession().flush();
	}

	@SuppressWarnings("unchecked")
	@Override
	public T get(Serializable id) {
		return (T) sessionFactory.getCurrentSession().get(getEntityClass(), id);
	}

	@Override
	public List<T> query(Cond cond) {
		return query(cond, null, null);
	}

	@Override
	public List<T> query(Cond cond, Sorter sorter) {
		return query(cond, null, sorter);
	}

	@Override
	public List<T> query(Cond cond, Pager pager, Sorter sorter) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				getEntityClass());
		prepare(criteria, cond, pager, sorter);
		criteria.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
		criteria.setFlushMode(FlushMode.COMMIT);
		return criteria.list();
	}

	/**
	 * Dealing with query condition, pagination and sorting before querying.
	 * 
	 * @param criteria
	 * @param cond
	 * @param pager
	 * @param sorter
	 */
	private void prepare(Criteria criteria, Cond cond, Pager pager,
			Sorter sorter) {
		// Using clone condition object because of creating aliases will change
		// the property of condition object.
		Cond cnd = cond != null ? cond.clone() : null;
		creatAlias(criteria, cnd, sorter);
		if (cnd != null) {
			Criterion criterion = cnd.toCriterion();
			if(criterion!=null) {
				criteria.add(criterion);
			}
		}
		page(criteria, pager);
		sort(criteria, sorter);
	}

	@SuppressWarnings("unchecked")
	private void page(Criteria criteria, Pager pager) {
		if (pager != null) {
			List<Long> countList = criteria.setProjection(
					Projections.rowCount()).list();
			int count = 0;
			for (Long c : countList) {
				count += c.intValue();
			}
			pager.setTotalCount(count);
			if (pager.getPage() > pager.getTotalPage()) {
				pager.setPage(pager.getTotalPage());
			}
			if (pager.getTotalCount() <= pager.getStartIndex()) {
				pager.setPage(1);
			}
			if (pager.getPage() == 1) {
				criteria.setFirstResult(pager.getStartIndex());
				criteria.setMaxResults(pager.getSize());
			} else {
				criteria.setFirstResult(pager.getStartIndex());
				criteria.setMaxResults(pager.getSize());
			}
			criteria.setProjection(null);
		}
	}

	private void sort(Criteria criteria, Sorter sorter) {
		if (sorter != null) {
			for (Sort sort : sorter) {
				if (sort.getName() != null && sort.getName().length() > 0) {
					String order = sort.getOrder();
					if ("desc".equalsIgnoreCase(order)) {
						criteria.addOrder(Order.desc(sort.getName()));
					} else {
						criteria.addOrder(Order.asc(sort.getName()));
					}
				}
			}
		}
	}

	private Map<String, String> creatAlias(Criteria criteria, Cond cond,
			Sorter sorter) {
		Map<String, String> aliasMap = new HashMap<String, String>();
		if (cond != null) {
			aliasMap.putAll(AliasHelper.getAliasMap(cond));
		}
		if (sorter != null) {
			aliasMap.putAll(AliasHelper.getAliasMap(sorter));
		}
		for (String key : aliasMap.keySet()) {
			criteria.createAlias(key, aliasMap.get(key),JoinType.LEFT_OUTER_JOIN);
		}
		return aliasMap;
	}

	@Override
	public T get(Cond cond) {
		List<T> list = query(cond);
		if(list.size()>0) {
			return list.get(0);
		}
		return null;
	}

	/**
	 * Query by hql clause.
	 * 
	 * @param hql
	 * @return
	 */
	@Deprecated
	public List find(String hql) {
		return sessionFactory.getCurrentSession().createQuery(hql).list();
	}

	/**
	 * Query by hql clause with parameters.
	 * 
	 * @param hql
	 * @param values
	 * @param types
	 * @return
	 */
	@Deprecated
	public List find(String hql, Object[] values, Type[] types) {
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameters(values, types);
		return query.list();
	}

}
