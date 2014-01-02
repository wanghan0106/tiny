package com.roy.tiny.base.dao.cond.property;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.roy.tiny.base.dao.cond.Cond;

public class EqCond extends PropertyCond {
	public EqCond(final String property,final Object value) {
		super(property,value);
	}
	
	@Override
	public Cond clone() {
		return new EqCond(property,value);
	}

	@Override
	public Criterion toCriterion() {
		if(value == null) {
			return Restrictions.isNull(property);
		}
		return Restrictions.eq(property, value);
	}

	@Override
	public DBObject toDBObject() {
		return new BasicDBObject(property,value);
	}
}
