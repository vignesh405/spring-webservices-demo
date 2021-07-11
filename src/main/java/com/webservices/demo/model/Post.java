package com.webservices.demo.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(value= {"secretInfo"})
@Entity
public class Post {
	
	@Id
	@GeneratedValue
	private Integer id;
	
	@NotBlank
	private String postName;
	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	@NotBlank
	private String postContent;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JsonIgnore
	private User user;
	
	private String secretInfo;
	public Post() {
		super();
	}
	public Post(Integer id, String postName, String postContent,String secretInfo) {
		super();
		this.id = id;
		this.postName = postName;
		this.postContent = postContent;
		this.secretInfo = secretInfo;
	}
	public String getSecretInfo() {
		return secretInfo;
	}
	public void setSecretInfo(String secretInfo) {
		this.secretInfo = secretInfo;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public void setPostName(String postName) {
		this.postName = postName;
	}
	public void setPostContent(String postContent) {
		this.postContent = postContent;
	}
	@Override
	public String toString() {
		return "Post [id=" + id + ", postName=" + postName + ", postContent=" + postContent + "]";
	}
	public Integer getId() {
		return id;
	}
	public String getPostName() {
		return postName;
	}
	public String getPostContent() {
		return postContent;
	}
}
