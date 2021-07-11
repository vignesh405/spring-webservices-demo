package com.webservices.demo.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.webservices.demo.exception.UserNotFound;
import com.webservices.demo.model.Post;
import com.webservices.demo.repository.PostRepository;

@RestController
public class PostJpaController {

	@Autowired
	private PostRepository postDAOService;
	
	@Autowired
	private MessageSource messageSource;
	
	@GetMapping("jpa/posts")
	public List<Post> getAll(){
		return postDAOService.findAll();
	}
	
	@GetMapping("jpa/posts/{id}")
	public Post getPostById(@PathVariable int id){
		Optional<Post> post = postDAOService.findById(id);
		if(!post.isPresent()) {
			throw new UserNotFound("id-"+id);
		}
		return post.get();
	}
	
	@PostMapping("jpa/posts")
	public ResponseEntity createPost(@Valid @RequestBody Post post) {
		Post savedPost = postDAOService.save(post);
		URI uri = ServletUriComponentsBuilder
					.fromCurrentRequest()
					.path("/{id}")
					.buildAndExpand(savedPost.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@GetMapping("jpa/posts/about")
	public String aboutPosts() {
		
		return messageSource.getMessage("about.posts.message", null, "Default Message", LocaleContextHolder.getLocale());
	}
	
	
}
