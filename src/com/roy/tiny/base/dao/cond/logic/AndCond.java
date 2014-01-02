package com.roy.tiny.base.dao.cond.logic;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.roy.tiny.base.dao.cond.Cond;

public class AndCond extends LogicCond {
	
	public AndCond(final Cond left,final Cond right) {
		super(left,right);
	}

	@Override
	public Cond clone() {
		return new AndCond(left!=null?left.clone():null,right!=null?right.clone():null);
	}

	@Override
	public Criterion toCriterion() {
		if(left!=null && right!=null) {
			return Restrictions.and(left.toCriterion(), right.toCriterion());
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
			if(leftObject.get("$and") !=null 
					&& leftObject.get("$and") instanceof BasicDBList 
					&& rightObject.get("$and") !=null 
					&& rightObject.get("$and") instanceof BasicDBList) {
				BasicDBList leftList = (BasicDBList) leftObject.get("$and");
				BasicDBList rightList = (BasicDBList) rightObject.get("$and");
				leftList.addAll(rightList);
				return leftObject;
			} else if(leftObject.get("$and") !=null && leftObject.get("$and") instanceof BasicDBList) {
				BasicDBList list = (BasicDBList) leftObject.get("$and");
				list.add(rightObject);
				return leftObject;
			} else if(rightObject.get("$and") !=null && rightObject.get("$and") instanceof BasicDBList) {
				BasicDBList list = (BasicDBList) rightObject.get("$and");
				list.add(leftObject);
				return rightObject;
			} else {
				BasicDBList list = new BasicDBList();
				list.add(leftObject);
				list.add(rightObject);
				dbObject.put("$and", list);
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
