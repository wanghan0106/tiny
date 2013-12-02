package com.roy.tiny.user.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.roy.tiny.base.dao.BaseDAO;
import com.roy.tiny.base.service.impl.BaseServiceImpl;
import com.roy.tiny.user.dao.UserDAO;
import com.roy.tiny.user.model.User;
import com.roy.tiny.user.service.UserService;

@Repository("userService")
public class UserServiceImpl extends BaseServiceImpl<User> implements UserService {
	
	@Autowired
	private UserDAO userDao;
	
	public UserDAO getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDAO userDao) {
		this.userDao = userDao;
	}

	@Override
	protected BaseDAO<User> getDao() {
		return userDao;
	}

}
