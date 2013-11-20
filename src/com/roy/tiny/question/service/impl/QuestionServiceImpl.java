package com.roy.tiny.question.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.roy.tiny.base.dao.BaseDAO;
import com.roy.tiny.base.service.impl.BaseServiceImpl;
import com.roy.tiny.question.dao.QuestionDAO;
import com.roy.tiny.question.model.Question;
import com.roy.tiny.question.service.QuestionService;

@Repository("questionService")
public class QuestionServiceImpl extends BaseServiceImpl<Question> implements QuestionService {
	
	private static final Logger log = LoggerFactory.getLogger(QuestionServiceImpl.class);
	
	@Autowired
	private QuestionDAO questionDao;

	protected BaseDAO<Question> getDao() {
		return questionDao;
	}

}
