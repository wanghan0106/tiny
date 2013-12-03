package com.roy.tiny.topic.service.impl;

import java.util.Date;
import java.util.List;

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
import com.roy.tiny.base.web.Pager;
import com.roy.tiny.base.web.Sorter;
import com.roy.tiny.topic.dao.CommentDAO;
import com.roy.tiny.topic.dao.TopicDAO;
import com.roy.tiny.topic.model.Comment;
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
	@Autowired
	private CommentDAO commentDao;


	public TopicDAO getTopicDao() {
		return topicDao;
	}

	public void setTopicDao(TopicDAO topicDao) {
		this.topicDao = topicDao;
	}

	public TextDAO getTextDao() {
		return textDao;
	}

	public void setTextDao(TextDAO textDao) {
		this.textDao = textDao;
	}

	public TagDAO getTagDao() {
		return tagDao;
	}

	public void setTagDao(TagDAO tagDao) {
		this.tagDao = tagDao;
	}

	public void save(Topic topic,User user) {
		Date date = new Date();
		textDao.save(topic.getText());
		topic.setCreateTime(date);
		topic.setUpdateTime(new Date());
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
	

	@Override
	public List<Comment> getComments(long topicId, Pager pager) {
		return commentDao.query(Cond.eq("topic.id", topicId), pager, new Sorter("createTime","asc"));
	}

	protected BaseDAO<Topic> getDao() {
		return topicDao;
	}

	@Override
	public void addComment(Comment comment, User user) {
		Date date = new Date();
		comment.setCreateTime(date);
		comment.setUser(user);
		comment.getTopic().setUpdateTime(date);
		comment.getTopic().setReplyNumber(comment.getTopic().getReplyNumber()+1);
		topicDao.save(comment.getTopic());
		textDao.save(comment.getText());
		commentDao.save(comment);
	}

	@Override
	public Comment getCommentById(long id) {
		return commentDao.get(id);
	}

	@Override
	public void delComment(Comment comment) {
		if(comment.getTopic().getReplyNumber()>0) {
			comment.getTopic().setReplyNumber(comment.getTopic().getReplyNumber()-1);
			topicDao.save(comment.getTopic());
		}
		commentDao.delete(comment);
		
	}
	
}
