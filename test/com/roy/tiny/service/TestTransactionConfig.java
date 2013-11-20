package com.roy.tiny.service;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.roy.tiny.TransactionalTestCase;
import com.roy.tiny.base.model.Text;
import com.roy.tiny.topic.model.Topic;
import com.roy.tiny.topic.service.TopicService;
import com.roy.tiny.user.model.User;
import com.roy.tiny.user.service.UserService;

public class TestTransactionConfig extends TransactionalTestCase {
	@Autowired
	private TopicService topicService;
	@Autowired
	private UserService userService;
	
	@Test
	public void testTransaction() {
		User user = new User();
		user.setUsername("roy");
		user.setNickname("wanghan");
		user.setPassword("111111");
		user.setSex(1);
		user.setBrief("自我介绍");
		userService.save(user);
		
		Topic topic = new Topic();
		topic.setTitle("测试");
		Text text = new Text();
		text.setContent("哈哈");
		topic.setText(text);
		topic.setTagNames("测试");
		topic.setCreateTime(new Date());
		topicService.save(topic,user);
	}
}
