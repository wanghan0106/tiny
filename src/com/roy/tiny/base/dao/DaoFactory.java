package com.roy.tiny.base.dao;

import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Entity;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.roy.tiny.base.dao.impl.BaseDAOImpl;
import com.roy.tiny.base.dao.impl.MongoDAOImpl;
import com.roy.tiny.util.AnnotationUtil;
import com.roy.tiny.util.PropertiesUtil;

@Repository("daoFactory")
public class DaoFactory {
	private static final Logger log = LoggerFactory.getLogger(DaoFactory.class);
	
	@Autowired
	protected SessionFactory sessionFactory;
	
	private Map<Class,BaseDAO> daoCache = new HashMap<Class,BaseDAO>();
	
	private final static String MONGO_RESOURCE = "mongo";
	
	public final static String COLLECTION_KEY = "key";
	
	private final static boolean MONGO_ENABLE = PropertiesUtil.getBooleanProperty("mongo.enable", MONGO_RESOURCE);
	
	private static DB db;
	
	static {
		if(MONGO_ENABLE) {
			initMongoDB();
		} 
	}

	private static void initMongoDB() {
		String host = PropertiesUtil.getProperty("mongo.host", MONGO_RESOURCE);
		int port = PropertiesUtil.getIntProperty("mongo.port", MONGO_RESOURCE);
		String dbName = PropertiesUtil.getProperty("mongo.db", MONGO_RESOURCE);
		MongoClient mongoClient;
		try {
			mongoClient = new MongoClient(host , port);
			db = mongoClient.getDB(dbName);
			DBCollection coll = db.getCollection(COLLECTION_KEY);
			List<Class> entityClassList = AnnotationUtil.getClassesByAnnotation(Entity.class);
			for(Class entityClass : entityClassList) {
				String collectionName = entityClass.getSimpleName().toLowerCase();
				DBObject object = coll.findOne(new BasicDBObject("name",collectionName));
				if(object == null) {
					coll.insert(new BasicDBObject("name",collectionName).append("id", 1));
				} else if(object.get("id")==null) {
					coll.update(new BasicDBObject("name",collectionName), new BasicDBObject("name",entityClass.getSimpleName()).append("id", 1));
				}
			}
		} catch (UnknownHostException e) {
			log.error("could not connect to mongodb at "+host+":"+port);
		}
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public BaseDAO getDao(Class entityClass) {
		BaseDAO dao = null;
		synchronized (daoCache) {
			dao = daoCache.get(entityClass);
			if(dao == null) {
				if(MONGO_ENABLE) {
					dao = getMongoDao(entityClass);
				} else {
					dao = getBaseDao(entityClass);
				}
			}
		}
		return dao;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private BaseDAO getBaseDao(Class entityClass) {
		BaseDAO dao;
		BaseDAOImpl daoImpl = new BaseDAOImpl();
		daoImpl.setEntityClass(entityClass);
		daoImpl.setSessionFactory(sessionFactory);
		daoCache.put(entityClass, daoImpl);
		dao = daoImpl;
		return dao;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private BaseDAO getMongoDao(Class entityClass) {
		BaseDAO dao;
		if(db == null) {
			return null;
		}
		MongoDAOImpl daoImpl = new MongoDAOImpl();
		daoImpl.setDb(db);
		daoImpl.setCollectionName(entityClass.getSimpleName().toLowerCase());
		daoImpl.setEntityClass(entityClass);
		daoCache.put(entityClass, daoImpl);
		dao = daoImpl;
		return dao;
	}

}
