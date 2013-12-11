package com.roy.tiny.dao;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import junit.framework.Assert;

import com.roy.tiny.TransactionalTestCase;
import com.roy.tiny.base.dao.BaseDAO;
import com.roy.tiny.base.dao.cond.Cond;
import com.roy.tiny.base.model.Text;
import com.roy.tiny.base.service.annotation.Dao;
import com.roy.tiny.topic.model.Topic;
import com.roy.tiny.user.model.User;

public class TestBaseDAOImpl extends TransactionalTestCase {
	
	@Dao
	private BaseDAO<User> userDao;
	@Dao
	private BaseDAO<Topic> topicDao;
	@Dao
	private BaseDAO<Text> textDao;
	
	//@Test
	public void testAddUser() {
		User user = new User();
		user.setUsername("roy");
		user.setNickname("wanghan");
		user.setPassword("111111");
		user.setSex(1);
		user.setBrief("自我介绍");
		userDao.save(user);
		
		List<User> userList = userDao.query(Cond.eq("username", "roy"));
		Assert.assertEquals("wanghan", userList.get(0).getNickname());
		Assert.assertEquals("wanghan", userDao.get(Cond.eq("username", "roy")).getNickname());
		
		/*userList = userDao.query(Cond.and(null, null));
		Assert.assertEquals("wanghan", userList.get(0).getNickname());
		userList = userDao.query(Cond.or(null, null));
		Assert.assertEquals("wanghan", userList.get(0).getNickname());
		userList = userDao.query(Cond.not(null));
		Assert.assertEquals("wanghan", userList.get(0).getNickname());
		userList = userDao.query(Cond.ne("username",null));
		Assert.assertEquals("wanghan", userList.get(0).getNickname());*/
		
		userDao.delete(user);
	}
	
	//@Test
	public void testAddTopic() {
		Topic topic = new Topic();
		topic.setTitle("aaa");
		Date date = new Date();
		topic.setCreateTime(date);
		topic.setCreateTime(date);
		Text text = new Text();
		text.setContent("aaabbb");
		textDao.save(text);
		topic.setText(text);
		topicDao.save(topic);
		Topic t2 = topicDao.get(Cond.eq("title", "aaa"));
		Assert.assertEquals("aaabbb", t2.getText().getContent());
		Assert.assertEquals("aaabbb", textDao.get(Cond.eq("content", "aaabbb")).getContent());
		topicDao.delete(topic);
		Assert.assertEquals(null, textDao.get(Cond.eq("content", "aaabbb")));
	}
	
	//@Test
	public void testAddFocusUser() {
		User u1 = userDao.get(Cond.eq("username", "roy042"));
		User u2 = userDao.get(Cond.eq("username", "duke"));
		Assert.assertNotNull(u1);
		Assert.assertNotNull(u2);
		u1.getFocusUsers().add(u2);
		userDao.save(u1);
		User u = userDao.get(Cond.eq("focusUsers.id", u2.getId()).and(Cond.eq("id", u1.getId())));
		Assert.assertNotNull(u);
		Assert.assertEquals(u1.getId(), u.getId());
		Iterator<User> iterator = u1.getFocusUsers().iterator();
		while(iterator.hasNext()) {
			User user = iterator.next();
			if(user.getId() == u2.getId()) {
				iterator.remove();
			}
		}
		userDao.save(u1);
		u = userDao.get(Cond.eq("focusUsers.id", u2.getId()).and(Cond.eq("id", u1.getId())));
		Assert.assertNull(u);
	}
	
	
	
	
	
}
