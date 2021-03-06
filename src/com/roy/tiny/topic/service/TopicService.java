package com.roy.tiny.topic.service;

import java.util.List;

import com.roy.tiny.base.service.BaseService;
import com.roy.tiny.base.web.Pager;
import com.roy.tiny.topic.model.Comment;
import com.roy.tiny.topic.model.Topic;
import com.roy.tiny.user.model.User;

public interface TopicService extends BaseService<Topic> {
	public void save(Topic topic,User user);
	public List<Comment> getComments(long topicId,Pager pager);
	public void addComment(Comment comment,User user);
	public Comment getCommentById(long id);
	public void delComment(Comment comment);
}
