package com.roy.tiny.base.dao.impl;

import org.springframework.stereotype.Repository;

import com.roy.tiny.base.dao.TagDAO;
import com.roy.tiny.base.model.Tag;

@Repository("tagDao")
public class TagDAOImpl extends BaseDAOImpl<Tag> implements TagDAO {

	public TagDAOImpl() {
		this.entityClass = Tag.class;
	}

}
