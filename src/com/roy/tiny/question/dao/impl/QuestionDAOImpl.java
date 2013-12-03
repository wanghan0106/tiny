package com.roy.tiny.question.dao.impl;

import org.springframework.stereotype.Repository;

import com.roy.tiny.base.dao.impl.BaseDAOImpl;
import com.roy.tiny.question.dao.QuestionDAO;
import com.roy.tiny.question.model.Question;

@Repository("questionDao")
public class QuestionDAOImpl extends BaseDAOImpl<Question> implements QuestionDAO {
}
