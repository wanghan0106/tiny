package com.roy.tiny.dao;

import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

import com.roy.tiny.TransactionalTestCase;
import com.roy.tiny.base.dao.BaseDAO;
import com.roy.tiny.base.dao.cond.Cond;
import com.roy.tiny.base.model.Tag;
import com.roy.tiny.base.service.annotation.Dao;
import com.roy.tiny.base.web.Pager;
import com.roy.tiny.base.web.Sorter;

public class TestMongoDAOImpl extends TransactionalTestCase {
	
	@Dao
	private BaseDAO<Tag> tagDao;
	
	//@Test
	public void testSave() {
		for(int i=0;i<20;i++) {
			Tag tag = new Tag();
			int index = i+1;
			tag.setName("tag"+index);
			tag.setPriority(index);
			tagDao.save(tag);
		}
	}
	
	@Test
	public void testGet() {
		Tag tag = tagDao.get(1L);
		Assert.assertNotNull(tag);
	}
	
	@Test
	public void testQuery() {
		List<Tag> tagList = tagDao.query(Cond.like("name", "tag%"));
		Assert.assertEquals(20, tagList.size());
		tagList = tagDao.query(Cond.gt("priority", 18));
		Assert.assertEquals(2, tagList.size());
		tagList = tagDao.query(Cond.ge("priority", 18));
		Assert.assertEquals(3, tagList.size());
		tagList = tagDao.query(Cond.eq("priority", 18));
		Assert.assertEquals(1, tagList.size());
		tagList = tagDao.query(Cond.lt("priority", 3));
		Assert.assertEquals(2, tagList.size());
		tagList = tagDao.query(Cond.le("priority", 3));
		Assert.assertEquals(3, tagList.size());
		tagList = tagDao.query(Cond.not(Cond.gt("priority", 3)));
		Assert.assertEquals(3, tagList.size());
		tagList = tagDao.query(Cond.and(Cond.gt("priority", 5), Cond.lt("priority", 9)));
		Assert.assertEquals(3, tagList.size());
		tagList = tagDao.query(Cond.or(Cond.lt("priority", 3), Cond.gt("priority", 18)));
		Assert.assertEquals(4, tagList.size());
		tagList = tagDao.query(Cond.or(Cond.and(Cond.gt("priority", 5), Cond.lt("priority", 9)),Cond.or(Cond.lt("priority", 3), Cond.gt("priority", 18))));
		Assert.assertEquals(7, tagList.size());
		tagList = tagDao.query(Cond.and(Cond.and(Cond.gt("priority", 5), Cond.lt("priority", 9)),Cond.and(Cond.gt("priority", 6), Cond.lt("priority", 18))));
		Assert.assertEquals(2, tagList.size());
		tagList = tagDao.query(Cond.not(Cond.or(Cond.and(Cond.gt("priority", 5), Cond.lt("priority", 9)),Cond.or(Cond.lt("priority", 3), Cond.gt("priority", 18)))));
		Assert.assertEquals(13, tagList.size());
		tagList = tagDao.query(Cond.in("name", new Object[]{"tag5","tag7","tag9"}));
		Assert.assertEquals(3, tagList.size());
		tagList = tagDao.query(Cond.ne("name","tag1"));
		Assert.assertEquals(19, tagList.size());
		tagList = tagDao.query(Cond.not(Cond.like("name","%1%")));
		Assert.assertEquals(9, tagList.size());
		tagList = tagDao.query(Cond.not(Cond.not(Cond.like("name","%1%"))));
		Assert.assertEquals(11, tagList.size());
		tagList = tagDao.query(Cond.lt("priority", 11),new Sorter("priority","desc"));
		Assert.assertEquals(10, tagList.get(0).getPriority());
		tagList = tagDao.query(Cond.lt("priority", 11),new Sorter("priority","asc"));
		Assert.assertEquals(1, tagList.get(0).getPriority());
		tagList = tagDao.query(Cond.like("name", "tag%"),new Sorter("priority","asc"));
		Assert.assertEquals(1, tagList.get(0).getPriority());
		Pager pager = new Pager(3);
		pager.setPage(2);
		tagList = tagDao.query(Cond.like("name", "tag%"),pager,new Sorter("priority","asc"));
		Assert.assertEquals(4, tagList.get(0).getPriority());
		pager = new Pager(5);
		pager.setPage(3);
		tagList = tagDao.query(Cond.like("name", "tag%"),pager,new Sorter("priority","asc"));
		Assert.assertEquals(11, tagList.get(0).getPriority());
	}
	
	@Test
	public void testGetByCond() {
		Tag tag = tagDao.get(Cond.eq("name", "tag2"));
		Assert.assertNotNull(tag);
	}
	
	//@Test
	public void testDelete() {
		Tag tag = tagDao.get(1L);
		Assert.assertNotNull(tag);
		tagDao.delete(tag);
		tag = tagDao.get(1L);
		Assert.assertNull(tag);
	}
}
