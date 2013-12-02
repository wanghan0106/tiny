package com.roy.tiny.user.model;

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
import javax.persistence.Table;

import com.roy.tiny.base.model.Model;

@Entity
@Table(name="user")
public class User extends Model {

	private static final long serialVersionUID = 1L;

	private long id;
	private String username;
	private String nickname;
	private String password;
	private String brief;
	private int sex; // 0 unknown,1 male, 2 female
	private String logo;
	private String location;
	private String favorite;
	private Date registerTime;
	private Date lastLoginTime;
	private List<User> focusUsers;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	@Column(name="username",length=200)
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	@Column(name="nickname",length=200)
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	@Column(name="password",length=100)
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@Column(name="brief",length=2000)
	public String getBrief() {
		return brief;
	}
	public void setBrief(String brief) {
		this.brief = brief;
	}
	@Column(name="sex")
	public int getSex() {
		return sex;
	}
	public void setSex(int sex) {
		this.sex = sex;
	}
	@Column(name="logo",length=500)
	public String getLogo() {
		return logo;
	}
	public void setLogo(String logo) {
		this.logo = logo;
	}
	@Column(name="location",length=500)
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	@Column(name="favorite",length=500)
	public String getFavorite() {
		return favorite;
	}
	public void setFavorite(String favorite) {
		this.favorite = favorite;
	}
	@Column(name="register_time")
	public Date getRegisterTime() {
		return registerTime;
	}
	public void setRegisterTime(Date registerTime) {
		this.registerTime = registerTime;
	}
	@Column(name="last_login_time")
	public Date getLastLoginTime() {
		return lastLoginTime;
	}
	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}
	
	@ManyToMany(cascade={CascadeType.PERSIST, CascadeType.MERGE,CascadeType.REMOVE},targetEntity=User.class,fetch=FetchType.LAZY)
	@JoinTable(name="user_focus_user",joinColumns=@JoinColumn(name="user_id"),inverseJoinColumns=@JoinColumn(name="focus_user_id"))
	public List<User> getFocusUsers() {
		return focusUsers;
	}
	public void setFocusUsers(List<User> focusUsers) {
		this.focusUsers = focusUsers;
	}
	

}
