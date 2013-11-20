package com.roy.tiny.topic.service.impl;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.roy.tiny.base.dao.BaseDAO;
import com.roy.tiny.base.dao.TagDAO;
import com.roy.tiny.base.dao.TextDAO;
import com.roy.tiny.base.dao.cond.Cond;
import com.roy.tiny.base.model.Tag;
import com.roy.tiny.base.service.impl.BaseServiceImpl;
import com.roy.tiny.topic.dao.TopicDAO;
import com.roy.tiny.topic.model.Topic;
import com.roy.tiny.topic.service.TopicService;
import com.roy.tiny.user.model.User;

@Repository("topicService")
public class TopicServiceImpl extends BaseServiceImpl<Topic> implements TopicService {
	
	private static final Logger log = LoggerFactory.getLogger(TopicServiceImpl.class);
	
	@Autowired
	private TopicDAO topicDao;
	@Autowired
	private TextDAO textDao;
	@Autowired
	private TagDAO tagDao;

	protected BaseDAO<Topic> getDao() {
		return topicDao;
	}

	public void save(Topic topic,User user) {
		textDao.save(topic.getText());
		topic.setCreateTime(new Date());
		topic.setUser(user);
		this.save(topic);
		if(topic.getTagNames()!=null && topic.getTagNames().length()>0) {
			String[] tagNames = topic.getTagNames().split(" ");
			for(String tagName : tagNames) {
				Tag tag = tagDao.get(Cond.eq("name", tagName));
				if(tag==null) {
					tag = new Tag();
					tag.setName(tagName);
					tag.setPriority(0);
					tagDao.save(tag);
				}
				tag.setPriority(tag.getPriority()+1);
				tagDao.save(tag);
				topic.getTags().add(tag);
			}
		}
		this.save(topic);
	}

}
