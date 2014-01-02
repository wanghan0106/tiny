package com.roy.tiny.base.dao.impl;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.roy.tiny.base.dao.BaseDAO;
import com.roy.tiny.base.dao.DaoFactory;
import com.roy.tiny.base.dao.cond.Cond;
import com.roy.tiny.base.model.Model;
import com.roy.tiny.base.web.Pager;
import com.roy.tiny.base.web.Sort;
import com.roy.tiny.base.web.Sorter;
import com.roy.tiny.exception.BusinessException;
import com.roy.tiny.util.JacksonUtil;

public class MongoDAOImpl<T extends Model> implements BaseDAO<T> {
	
	private static final Logger log = LoggerFactory
			.getLogger(MongoDAOImpl.class);
	
	private DB db;
	private String collectionName;
	private Class entityClass;

	public DB getDb() {
		return db;
	}

	public void setDb(DB db) {
		this.db = db;
	}

	public Class getEntityClass() {
		return entityClass;
	}

	public void setEntityClass(Class entityClass) {
		this.entityClass = entityClass;
	}

	public String getCollectionName() {
		if(collectionName == null) {
			java.lang.reflect.Type type = this.getClass().getGenericSuperclass();
			if(type instanceof ParameterizedType) {
				ParameterizedType ptype = (ParameterizedType) type;
				Class clazz = (Class<T>) ptype.getActualTypeArguments()[0];
				collectionName = clazz.getSimpleName().toLowerCase();
			}
		}
		return collectionName;
	}

	public void setCollectionName(String collectionName) {
		this.collectionName = collectionName;
	}

	@Override
	public void save(T object) {
		DBCollection coll = db.getCollection(collectionName);
		DBCollection idColl = db.getCollection(DaoFactory.COLLECTION_KEY);
		try {
			DBObject idObject = idColl.findAndModify(new BasicDBObject("name", collectionName), new BasicDBObject("$inc", new BasicDBObject("id",1)));
			object.setId(Long.parseLong(Integer.toString((Integer) idObject.get("id"))));
			DBObject dbObject = JacksonUtil.toDBObject(object);
			coll.insert(dbObject);
		} catch (IOException e) {
			log.error("MongoDAOImpl.save():"+e.getMessage());
			throw new BusinessException(e.getMessage());
		}
		
	}

	@Override
	public void delete(T object) {
		DBCollection coll = db.getCollection(collectionName);
		coll.findAndRemove(new BasicDBObject("id",object.getId()));
	}

	@SuppressWarnings("unchecked")
	@Override
	public T get(Serializable id) {
		DBCollection coll = db.getCollection(collectionName);
		DBObject dbObject = coll.findOne(new BasicDBObject("id",Integer.parseInt(id.toString())));
		if(dbObject==null) {
			return null;
		}
		try {
			return (T) JacksonUtil.toPojo(entityClass, dbObject);
		} catch (IOException e) {
			log.error("MongoDAOImpl.get():"+e.getMessage());
			throw new BusinessException(e.getMessage());
		}
	}

	@Override
	public List<T> query(Cond cond) {
		DBCollection coll = db.getCollection(collectionName);
		if(cond!=null) {
			DBObject query = cond.toDBObject();
			if(query!=null) {
				return toList(coll.find(cond.toDBObject()));
			}
		}
		return toList(coll.find());
	}

	@Override
	public List<T> query(Cond cond, Sorter sorter) {
		if(sorter==null || sorter.size()==0) {
			return query(cond);
		}
		DBCollection coll = db.getCollection(collectionName);
		BasicDBObject sortObject = toDBObject(sorter);
		if(cond!=null) {
			DBObject query = cond.toDBObject();
			if(query!=null) {
				return toList(coll.find(cond.toDBObject()).sort(sortObject));
			}
		}
		return toList(coll.find().sort(sortObject));
	}

	@Override
	public List<T> query(Cond cond, Pager pager, Sorter sorter) {
		if(pager==null) {
			return query(cond,sorter);
		}
		DBCollection coll = db.getCollection(collectionName);
		Cond cnd = cond!=null?cond.clone():null;
		long count = cnd!=null?coll.count(cnd.toDBObject()):coll.count();
		pager.setTotalCount(Integer.parseInt(Long.toString(count)));
		if (pager.getPage() > pager.getTotalPage()) {
			pager.setPage(pager.getTotalPage());
		}
		if (pager.getTotalCount() <= pager.getStartIndex()) {
			pager.setPage(1);
		}
		DBObject sortObject = toDBObject(sorter);
		DBObject query = null;
		if(cond!=null) {
			query = cond.toDBObject();
		}
		if(sortObject!=null) {
			if(query!=null) {
				return toList(coll.find(query).sort(sortObject).skip(pager.getStartIndex()).limit(pager.getSize()));
			} else {
				return toList(coll.find().sort(sortObject).skip(pager.getStartIndex()).limit(pager.getSize()));
			}
		} else {
			if(query!=null) {
				return toList(coll.find(query).skip(pager.getStartIndex()).limit(pager.getSize()));
			} else {
				return toList(coll.find().sort(sortObject).skip(pager.getStartIndex()).limit(pager.getSize()));
			}
		}
	}

	@Override
	public T get(Cond cond) {
		List<T> list = query(cond);
		if(list.size()==1) {
			return list.get(0);
		}
		return null;
	}
	
	@SuppressWarnings({ "unused", "unchecked" })
	private List<T> toList(DBCursor cursor) {
		List<T> list = new ArrayList<T>();
		try {
		   while(cursor.hasNext()) {
			   DBObject object = cursor.next();
			   try {
				   list.add((T) JacksonUtil.toPojo(entityClass, object));
			   } catch(IOException e) {
				   log.error("Could not parse MongoDB object to pojo:"+e.getMessage());
			   } 
		   }
		} finally {
		   cursor.close();
		}
		return list;
	}
	
	private BasicDBObject toDBObject(Sorter sorter) {
		if(sorter==null || sorter.size()==0) {
			return null;
		}
		BasicDBObject sortObject = new BasicDBObject();
		if(sorter!=null && sorter.size()>0) {
			for(Sort sort : sorter) {
				if(sort!=null && sort.getName()!=null) {
					if("desc".equalsIgnoreCase(sort.getOrder())) {
						sortObject.put(sort.getName(), -1);
					} else {
						sortObject.put(sort.getName(), 1);
					}
				}
			}
		}
		return sortObject;
	}

}
