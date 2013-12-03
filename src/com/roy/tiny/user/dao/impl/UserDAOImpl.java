package com.roy.tiny.user.dao.impl;

import org.springframework.stereotype.Repository;

import com.roy.tiny.base.dao.impl.BaseDAOImpl;
import com.roy.tiny.user.dao.UserDAO;
import com.roy.tiny.user.model.User;

@Repository("userDao")
public class UserDAOImpl extends BaseDAOImpl<User> implements UserDAO {
}
