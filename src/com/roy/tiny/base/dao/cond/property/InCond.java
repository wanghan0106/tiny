package com.roy.tiny.base.dao.cond.property;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.roy.tiny.base.dao.cond.Cond;


public class InCond extends PropertyCond {
	public InCond(final String property,final Object[] values) {
		super(property,values);
	}
	
	@Override
	public Cond clone() {
		return new InCond(property,(Object[]) value);
	}
	
	@Override
	public Criterion toCriterion() {
		return Restrictions.in(property, (Object[]) value);
	}

	@Override
	public DBObject toDBObject() {
		if(value!=null) {
			BasicDBList list = new BasicDBList();
			Object[] values = (Object[]) value;
			for(Object object : values) {
				list.add(object);
			}
			return new BasicDBObject(property,new BasicDBObject("$in",list));
		}
		return null;
	}
}
