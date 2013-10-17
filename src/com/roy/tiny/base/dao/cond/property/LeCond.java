package com.roy.tiny.base.dao.cond.property;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import com.roy.tiny.base.dao.cond.Cond;

public class LeCond extends PropertyCond {
	public LeCond(final String property,final Object value) {
		super(property,value);
	}
	
	@Override
	public Cond clone() {
		return new LeCond(property,value);
	}
	
	@Override
	public Criterion toCriterion() {
		return Restrictions.le(property, value);
	}
}
