package com.roy.tiny.base.web;

import java.util.ArrayList;

public class Sorter extends ArrayList<Sort> {

	private static final long serialVersionUID = -4844859368345777150L;
	
	public Sorter() {
		super();
	}
	
	public Sorter(Sort sort) {
		this();
		this.add(sort);
	}
	

}
