package com.roy.tiny.topic.service;

import com.roy.tiny.base.service.BaseService;
import com.roy.tiny.topic.model.Topic;
import com.roy.tiny.user.model.User;

public interface TopicService extends BaseService<Topic> {
	public void save(Topic topic,User user);
}
