package com.roy.tiny.base.dao.cond.property;

import com.roy.tiny.base.dao.cond.Cond;

public abstract class PropertyCond extends Cond {
	protected String property;
	protected Object value;
	
	protected PropertyCond(final String property,final Object value) {
		this.property = property;
		this.value = value;
	}
	
	public String getProperty() {
		return property;
	}
	public void setProperty(String property) {
		this.property = property;
	}
	public Object getValue() {
		return value;
	}
	public void setValue(Object value) {
		this.value = value;
	}
	
}
