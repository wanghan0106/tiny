package com.roy.tiny.topic.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.roy.tiny.base.model.Model;
import com.roy.tiny.base.model.Text;
import com.roy.tiny.user.model.User;

@Entity
@Table(name="comment")
public class Comment extends Model {
	private static final long serialVersionUID = 1L;
	
	private long id;
	private Text text;
	private Date createTime;
	private User user;
	private Topic topic;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	@ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE,CascadeType.REMOVE} )
    @JoinColumn(name="text_id")
	public Text getText() {
		return text;
	}
	public void setText(Text text) {
		this.text = text;
	}
	@Column(name="create_time")
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	@ManyToOne(cascade = {CascadeType.PERSIST} )
    @JoinColumn(name="user_id")
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	@ManyToOne(cascade = {CascadeType.PERSIST} )
    @JoinColumn(name="topic_id")
	public Topic getTopic() {
		return topic;
	}
	public void setTopic(Topic topic) {
		this.topic = topic;
	}
	
}
