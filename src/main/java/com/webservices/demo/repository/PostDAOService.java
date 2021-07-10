package com.webservices.demo.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.webservices.demo.model.Post;

@Component
public class PostDAOService {

	private static List<Post> posts = new ArrayList<>();
	private static int postCount;
	static {
		posts.add(new Post(1,"post1","sample content 1"));
		posts.add(new Post(2,"post2","sample content 2"));
		posts.add(new Post(3,"post3","sample content 3"));
		postCount = 3;
	}
	
	public List<Post> findAll(){
		return posts;
	}
	
	public Post findById(int id) {
		for (Post post : posts) {
			if(post.getId() == id) {
				return post;
			}
		}
		return null;
	}
	
	public Post save(Post post) {
		if(post.getId() == null) {
			post.setId(++postCount);
		}
		posts.add(post);
		return post;
		
	}
}
