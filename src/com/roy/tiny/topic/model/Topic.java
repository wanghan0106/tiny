package com.roy.tiny.topic.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.roy.tiny.base.model.Model;
import com.roy.tiny.base.model.Tag;
import com.roy.tiny.base.model.Text;
import com.roy.tiny.user.model.User;

@Entity
@Table(name="topic")
public class Topic extends Model {
	private static final long serialVersionUID = 1L;

	private long id;
	private String title;
	private Date createTime;
	private Date updateTime;
	private User user;
	private Text text;
	private List<Tag> tags = new ArrayList<Tag>();
	private int replyNumber = 0;
	private String tagNames;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	@Column(name="title",length=200)
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	@Column(name="create_time")
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	@Column(name="update_time")
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	@ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE,CascadeType.REMOVE} )
    @JoinColumn(name="text_id")
	public Text getText() {
		return text;
	}
	public void setText(Text text) {
		this.text = text;
	}
	
	@ManyToOne(cascade = {CascadeType.PERSIST} )
    @JoinColumn(name="user_id")
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	@ManyToMany(cascade={CascadeType.PERSIST, CascadeType.MERGE,CascadeType.REMOVE},targetEntity=Tag.class,fetch=FetchType.LAZY)
	@JoinTable(name="topic_tags",joinColumns=@JoinColumn(name="topic_id"),inverseJoinColumns=@JoinColumn(name="tag_id"))
	public List<Tag> getTags() {
		return tags;
	}
	public void setTags(List<Tag> tags) {
		this.tags = tags;
	}
	@Column(name="reply_number")
	public int getReplyNumber() {
		return replyNumber;
	}
	public void setReplyNumber(int replyNumber) {
		this.replyNumber = replyNumber;
	}
	public String getTagNames() {
		return tagNames;
	}
	public void setTagNames(String tagNames) {
		this.tagNames = tagNames;
	}
	
}
