package com.roy.tiny.base.web;

import java.util.ArrayList;
import java.util.List;

public class Pager implements java.io.Serializable {
	
	private static final long serialVersionUID = 4917335862512570519L;
	private static final int SIZE = 10;
	private int size;
	private int page = 1;
	private int totalPage;
	private int totalCount;
	private List<Integer> pages = new ArrayList<Integer>();
	
	public Pager() {
		this.size = SIZE;
	}
	
	public Pager(final int size) {
		this.size = size;
	}

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
		if(totalPage==0) {
			totalPage = 1;
		}
	}
	
	public int getStartIndex() {
		if(page ==0){
			return 0;
		}
		return (page - 1) * size;
	}

	public List<Integer> getPages() {
		if(pages.size()==0) {
			int start = page - 2;
		    int end = page + 2;
		    if (start < 1) {
		        start = 1;
		        end = 5;
		    }
		    if (end > totalPage) {
		        end = totalPage;
		        start = end - 4;
		        if (start < 1) {
		            start = 1;
		        }
		    }
		    for(int i=start;i<=end;i++) {
		    	pages.add(i);
		    }
		}
		return pages;
	}

	public void setPages(List<Integer> pages) {
		this.pages = pages;
	}
	
	
	

}
