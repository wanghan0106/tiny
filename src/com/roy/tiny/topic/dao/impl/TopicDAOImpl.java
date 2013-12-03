package com.roy.tiny.topic.dao.impl;

import org.springframework.stereotype.Repository;

import com.roy.tiny.base.dao.impl.BaseDAOImpl;
import com.roy.tiny.topic.dao.TopicDAO;
import com.roy.tiny.topic.model.Topic;

@Repository("topicDao")
public class TopicDAOImpl extends BaseDAOImpl<Topic> implements TopicDAO {
}
