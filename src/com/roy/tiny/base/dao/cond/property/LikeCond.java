package com.roy.tiny.base.dao.cond.property;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import com.roy.tiny.base.dao.cond.Cond;

public class LikeCond extends PropertyCond {
	public LikeCond(final String property,final String value) {
		super(property,value);
	}
	
	@Override
	public Cond clone() {
		return new LikeCond(property,(String) value);
	}
	
	@Override
	public Criterion toCriterion() {
		return Restrictions.like(property, value);
	}
}
