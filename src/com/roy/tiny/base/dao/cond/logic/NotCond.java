package com.roy.tiny.base.dao.cond.logic;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.roy.tiny.base.dao.cond.Cond;
import com.roy.tiny.base.dao.cond.property.PropertyCond;

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

	@Override
	public DBObject toDBObject() {
		if(cond!=null) {
			DBObject object = cond.toDBObject();
			if(object!=null) {
				if(cond instanceof PropertyCond) {
					PropertyCond cnd = (PropertyCond) cond;
					Object o = object.get(cnd.getProperty());
					if(o!=null) {
						return new BasicDBObject(cnd.getProperty(),new BasicDBObject("$not",o));
					}
				} else if(cond instanceof NotCond) {
					NotCond cnd = (NotCond) cond;
					for(String key : object.keySet()) {
						Object value = (DBObject) object.get(key);
						if(value!=null && value instanceof DBObject) {
							DBObject dbObject = (DBObject) value;
							Object o = dbObject.get("$not");
							if(o!=null) {
								return new BasicDBObject(key,o);
							}
						}
					}
				} else if(cond instanceof AndCond) {
					AndCond cnd = (AndCond) cond;
					return new OrCond(new NotCond(cnd.getLeft()),new NotCond(cnd.getRight())).toDBObject();
				} else if(cond instanceof OrCond) {
					OrCond cnd = (OrCond) cond;
					return new AndCond(new NotCond(cnd.getLeft()),new NotCond(cnd.getRight())).toDBObject();
				}
			}
		}
		return null;
	}
	
	
	
	
}
