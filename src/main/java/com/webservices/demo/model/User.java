package com.webservices.demo.model;

import java.util.Date;

import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;

@JsonFilter("UserFilter")
public class User {
	private Integer id;
	
	@Size(min=2,message="Name should have atlease 2 characters")
	private String name;
	
	@Past
	private Date birthDate;
	
	@JsonIgnore
	private String secrectInfo;
	
	public String getSecrectInfo2() {
		return secrectInfo2;
	}
	public void setSecrectInfo2(String secrectInfo2) {
		this.secrectInfo2 = secrectInfo2;
	}
	private String secrectInfo2;
	
	private String openInfo;
	
	public String getOpenInfo() {
		return openInfo;
	}
	public void setOpenInfo(String openInfo) {
		this.openInfo = openInfo;
	}
	public User(Integer id, String name, Date birthDate,String secretInfo,String secretInfo2,String openInfo) {
		super();
		this.id = id;
		this.name = name;
		this.birthDate = birthDate;
		this.secrectInfo = secretInfo;
		this.secrectInfo2 = secretInfo2;
		this.openInfo = openInfo;
	}
	public String getSecrectInfo() {
		return secrectInfo;
	}
	public void setSecrectInfo(String secrectInfo) {
		this.secrectInfo = secrectInfo;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", birthDate=" + birthDate + "]";
	}
	public Integer getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public Date getBirthDate() {
		return birthDate;
	}
}
