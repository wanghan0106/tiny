package com.roy.tiny.topic.dao.impl;

import org.springframework.stereotype.Repository;

import com.roy.tiny.base.dao.impl.BaseDAOImpl;
import com.roy.tiny.topic.dao.CommentDAO;
import com.roy.tiny.topic.model.Comment;

@Repository("commentDao")
public class CommentDAOImpl extends BaseDAOImpl<Comment> implements CommentDAO {
}
