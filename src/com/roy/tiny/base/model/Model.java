package com.roy.tiny.base.model;

import java.io.Serializable;

public abstract class Model implements java.io.Serializable {
	public abstract long getId();
	public abstract void setId(long id);
}
