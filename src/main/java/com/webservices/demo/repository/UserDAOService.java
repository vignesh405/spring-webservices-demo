package com.webservices.demo.repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ListIterator;

import org.springframework.stereotype.Component;

import com.webservices.demo.model.User;

@Component
public class UserDAOService {
	private static List<User> users = new ArrayList<>();
	private static int userCount = 3;
	static {
		users.add(new User(1,"Adam",new Date()));
		users.add(new User(2,"Bill",new Date()));
		users.add(new User(3,"Cat",new Date()));
	}
	
	public List<User> findAll(){
		return users;
	}
	
	public User save(User user) {
		if(user.getId() == null) {
			user.setId(++userCount);
		}
		users.add(user);
		return user;
	}

	public User findOne(int id) {
		// TODO Auto-generated method stub
		for (User user : users) {
			if(user.getId() == id) {
				return user;
			}
		}
		return null;
	}
	
	public boolean deleteById(int id) {
		ListIterator<User> userIterator = users.listIterator();
		boolean idFound = false;
		while(userIterator.hasNext()) {
			if(userIterator.next().getId()==id) {
				idFound = true;
				userIterator.remove();
				break;
			}
		}
		return idFound;
	}
}
