package com.roy.tiny.base.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.roy.tiny.base.dao.BaseDAO;
import com.roy.tiny.base.dao.TagDAO;
import com.roy.tiny.base.model.Tag;
import com.roy.tiny.base.service.TagService;
import com.roy.tiny.base.web.Pager;
import com.roy.tiny.base.web.Sorter;

@Repository("tagService")
public class TagServiceImpl extends BaseServiceImpl<Tag> implements TagService {
	
	@Autowired
	private TagDAO tagDao;

	@Override
	protected BaseDAO<Tag> getDao() {
		return tagDao;
	}

	@Override
	public List<Tag> getPopularTags() {
		return this.query(null, new Pager(10), new Sorter("priority","desc"));
	}

}
