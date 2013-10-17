package com.roy.tiny.topic.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.roy.tiny.base.dao.BaseDAO;
import com.roy.tiny.base.service.impl.BaseServiceImpl;
import com.roy.tiny.topic.dao.TopicDAO;
import com.roy.tiny.topic.model.Topic;
import com.roy.tiny.topic.service.TopicService;

@Repository("topicService")
public class TopicServiceImpl extends BaseServiceImpl<Topic> implements TopicService {
	
	private static final Logger log = LoggerFactory.getLogger(TopicServiceImpl.class);
	
	@Autowired
	private TopicDAO topicDao;

	protected BaseDAO<Topic> getDao() {
		return topicDao;
	}

}
