package com.webservices.demo.controller;

import java.net.URI;
import java.util.List;
import java.util.Locale;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.MessageSourceResourceBundle;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.webservices.demo.exception.UserNotFound;
import com.webservices.demo.model.Post;
import com.webservices.demo.repository.PostDAOService;

@RestController
public class PostController {

	@Autowired
	private PostDAOService postDAOService;
	
	@Autowired
	private MessageSource messageSource;
	
	@GetMapping("/posts")
	public List<Post> getAll(){
		return postDAOService.findAll();
	}
	
	@GetMapping("/posts/{id}")
	public Post getPostById(@PathVariable int id){
		Post post = postDAOService.findById(id);
		if(post==null) {
			throw new UserNotFound("id-"+id);
		}
		return post;
	}
	
	@PostMapping("/posts")
	public ResponseEntity createPost(@Valid @RequestBody Post post) {
		Post savedPost = postDAOService.save(post);
		URI uri = ServletUriComponentsBuilder
					.fromCurrentRequest()
					.path("/{id}")
					.buildAndExpand(savedPost.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@GetMapping("/posts/about")
	public String aboutPosts() {
		
		return messageSource.getMessage("about.posts.message", null, "Default Message", LocaleContextHolder.getLocale());
	}
	
	
}
