package com.roy.tiny.base.web;

public class Pager implements java.io.Serializable {
	
	private static final long serialVersionUID = 4917335862512570519L;
	
	private int size;
	private int page;
	private int totalPage;
	private int totalCount;

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
		if (size != 0){
			totalPage = ( totalCount + size - 1 ) / size;
		}
	}
	
	public int getStartIndex() {
		if(page ==0){
			return 0;
		}
		return (page - 1) * size;
	}
	
	
	

}
