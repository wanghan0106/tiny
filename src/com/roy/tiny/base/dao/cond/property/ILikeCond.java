package com.roy.tiny.base.dao.cond.property;

import java.util.regex.Pattern;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.roy.tiny.base.dao.cond.Cond;

public class ILikeCond extends PropertyCond {
	public ILikeCond(final String property,final String value) {
		super(property,value);
	}
	
	@Override
	public Cond clone() {
		return new ILikeCond(property,(String) value);
	}
	
	@Override
	public Criterion toCriterion() {
		return Restrictions.ilike(property, value);
	}
	
	@Override
	public DBObject toDBObject() {
		if(value!=null) {
			return new BasicDBObject(property,Pattern.compile(("^.*"+value+".*$").toLowerCase()));
		}
		return null;
	}
}
