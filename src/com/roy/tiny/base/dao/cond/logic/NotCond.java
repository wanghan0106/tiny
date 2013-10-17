package com.roy.tiny.base.dao.cond.logic;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import com.roy.tiny.base.dao.cond.Cond;

public class NotCond extends Cond {
	protected Cond cond;
	
	public NotCond(Cond cond) {
		this.cond = cond;
	}

	public Cond getCond() {
		return cond;
	}

	public void setCond(Cond cond) {
		this.cond = cond;
	}

	@Override
	public Cond clone() {
		return new NotCond(cond!=null?cond.clone():null);
	}
	
	@Override
	public Criterion toCriterion() {
		if(cond!=null) {
			return Restrictions.not(cond.toCriterion());
		} else {
			return null;
		}
		
	}
	
	
}
