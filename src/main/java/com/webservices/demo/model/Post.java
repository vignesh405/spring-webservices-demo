package com.webservices.demo.model;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(value= {"secretInfo"})
public class Post {
	private Integer id;
	
	@NotBlank
	private String postName;
	
	@NotBlank
	private String postContent;
	
	private String secretInfo;
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
