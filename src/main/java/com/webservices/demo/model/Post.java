package com.webservices.demo.model;

import javax.validation.constraints.NotBlank;

public class Post {
	private Integer id;
	
	@NotBlank
	private String postName;
	
	@NotBlank
	private String postContent;
	public Post(Integer id, String postName, String postContent) {
		super();
		this.id = id;
		this.postName = postName;
		this.postContent = postContent;
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
