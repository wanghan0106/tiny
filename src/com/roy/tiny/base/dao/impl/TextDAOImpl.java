package com.roy.tiny.base.dao.impl;

import org.springframework.stereotype.Repository;

import com.roy.tiny.base.dao.TextDAO;
import com.roy.tiny.base.model.Text;
import com.roy.tiny.user.model.User;

@Repository("textDao")
public class TextDAOImpl extends BaseDAOImpl<Text> implements TextDAO {
}
