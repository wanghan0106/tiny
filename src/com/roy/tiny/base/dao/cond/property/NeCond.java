package com.roy.tiny.base.dao.cond.property;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.roy.tiny.base.dao.cond.Cond;

public class NeCond extends PropertyCond {
	public NeCond(final String property,final Object value) {
		super(property,value);
	}
	
	@Override
	public Cond clone() {
		return new NeCond(property,value);
	}

	@Override
	public Criterion toCriterion() {
		if(value == null) {
			return Restrictions.isNotNull(property);
		}
		return Restrictions.ne(property, value);
	}
	
	@Override
	public DBObject toDBObject() {
		return new BasicDBObject(property,new BasicDBObject("$ne",value));
	}
}
