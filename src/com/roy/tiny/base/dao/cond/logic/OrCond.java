package com.roy.tiny.base.dao.cond.logic;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
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
	
	@Override
	public DBObject toDBObject() {
		if(left!=null && right!=null) {
			BasicDBObject dbObject = new BasicDBObject();
			DBObject leftObject = left.toDBObject();
			DBObject rightObject = right.toDBObject();
			if(leftObject == null && rightObject == null) {
				return null;
			} else if(leftObject == null) {
				return rightObject;
			} else if(rightObject == null) {
				return leftObject;
			}
			if(leftObject.get("$or") !=null 
					&& leftObject.get("$or") instanceof BasicDBList 
					&& rightObject.get("$or") !=null 
					&& rightObject.get("$or") instanceof BasicDBList) {
				BasicDBList leftList = (BasicDBList) leftObject.get("$or");
				BasicDBList rightList = (BasicDBList) rightObject.get("$or");
				leftList.addAll(rightList);
				return leftObject;
			} else if(leftObject.get("$or") !=null && leftObject.get("$or") instanceof BasicDBList) {
				BasicDBList list = (BasicDBList) leftObject.get("$or");
				list.add(rightObject);
				return leftObject;
			} else if(rightObject.get("$or") !=null && rightObject.get("$or") instanceof BasicDBList) {
				BasicDBList list = (BasicDBList) rightObject.get("$or");
				list.add(leftObject);
				return rightObject;
			} else {
				BasicDBList list = new BasicDBList();
				list.add(leftObject);
				list.add(rightObject);
				dbObject.put("$or", list);
			}
			return dbObject;
		} else if(left!=null) {
			return left.toDBObject();
		} else if(right!=null) {
			return right.toDBObject();
		} else {
			return null;
		}
	}
	
}
