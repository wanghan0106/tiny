package com.roy.tiny.base.dao.cond.property;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import com.roy.tiny.base.dao.cond.Cond;

public class GtCond extends PropertyCond {
	public GtCond(final String property,final Object value) {
		super(property,value);
	}
	
	@Override
	public Cond clone() {
		return new GtCond(property,value);
	}
	
	@Override
	public Criterion toCriterion() {
		return Restrictions.gt(property, value);
	}
}
