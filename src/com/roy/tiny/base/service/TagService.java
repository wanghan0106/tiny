package com.roy.tiny.base.service;

import java.util.List;

import com.roy.tiny.base.model.Tag;

public interface TagService extends BaseService<Tag> {
	public List<Tag> getPopularTags();
}
