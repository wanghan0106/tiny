package com.roy.tiny.base.dao.cond.logic;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import com.roy.tiny.base.dao.cond.Cond;

public class OrCond extends LogicCond {
	
	public OrCond(final Cond left,final Cond right) {
		super(left,right);
	}

	@Override
	public Cond clone() {
		return new OrCond(left!=null?left.clone():null,right!=null?right.clone():null);
	}
	
	@Override
	public Criterion toCriterion() {
		if(left!=null && right!=null) {
			return Restrictions.or(left.toCriterion(), right.toCriterion());
		} else if(left!=null) {
			return left.toCriterion();
		} else if(right!=null) {
			return right.toCriterion();
		} else {
			return null;
		}
	}
	
}
