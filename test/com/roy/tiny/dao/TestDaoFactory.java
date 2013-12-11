package com.roy.tiny.dao;

import java.util.List;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.roy.tiny.TransactionalTestCase;
import com.roy.tiny.base.dao.BaseDAO;
import com.roy.tiny.base.dao.DaoFactory;
import com.roy.tiny.base.dao.cond.Cond;
import com.roy.tiny.base.service.annotation.Dao;
import com.roy.tiny.user.model.User;
import com.roy.tiny.user.service.UserService;

public class TestDaoFactory extends TransactionalTestCase {
	
	@Autowired
	private DaoFactory daoFactory;
	@Dao
	private BaseDAO<User> userDao;
	@Autowired
	private UserService userService;
	
	@Test
	public void testGetDao() {
		BaseDAO<User> dao = daoFactory.getDao(User.class);
		List<User> userList = dao.query(Cond.eq("sex", 0));
		BaseDAO<User> dao2 = daoFactory.getDao(User.class);
		Assert.assertNotNull(userList);
		Assert.assertEquals(dao, dao2);
		userList = userDao.query(Cond.eq("sex", 0));
		Assert.assertNotNull(userList);
		userList = userService.query(Cond.eq("sex", 0));
		Assert.assertNotNull(userList);
	}

}
