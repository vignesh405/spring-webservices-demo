package com.webservices.demo.controller;

import java.net.URI;
import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.webservices.demo.exception.UserNotFound;
import com.webservices.demo.filters.FilterHelper;
import com.webservices.demo.model.Post;
import com.webservices.demo.model.User;
import com.webservices.demo.repository.PostRepository;
import com.webservices.demo.repository.UserDAOService;
import com.webservices.demo.repository.UserRepository;



@RestController
public class UserJpaController {

	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PostRepository postRepository;
	
	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private FilterHelper filterHelper;
	
	@GetMapping("/jpa/users")
	public MappingJacksonValue retrieveAll(){
		List<User> userList = userRepository.findAll();
		return filterHelper.getUserMapping(userList);
	}
	
	@GetMapping("/jpa/users/{id}")
	public MappingJacksonValue retrieveUser(@PathVariable int id) {
		Optional<User> user = userRepository.findById(id);
		if(!user.isPresent())
			throw new UserNotFound("id-"+id);
		
		EntityModel<Optional<User>> model = EntityModel.of(user);
		WebMvcLinkBuilder linkToUsers = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).retrieveAll());
		model.add(linkToUsers.withRel("all-users"));
		return filterHelper.getUserMapping(model);
	}
	
	@PostMapping("/jpa/users")
	public ResponseEntity postUser(@Valid @RequestBody User user) {
		User savedUser = userRepository.save(user);
		URI uri = ServletUriComponentsBuilder
			.fromCurrentRequest()
			.path("/{id}")
			.buildAndExpand(savedUser.getId()).toUri();
		EntityModel<User> model = EntityModel.of(savedUser);
		WebMvcLinkBuilder linkToSavedUser = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).retrieveUser(savedUser.getId()));
		model.add(linkToSavedUser.withRel("saved-user"));
		return ResponseEntity.created(uri).body(filterHelper.getUserMapping(model));
	}
	
	@DeleteMapping("/jpa/users/{id}")
	public void deleteUser(@PathVariable int id) {
		userRepository.deleteById(id);
	}
	
	@GetMapping("/jpa/users/about")
	public String aboutUsers() {
		
		return messageSource.getMessage("about.users.message", null, "Default Message", LocaleContextHolder.getLocale());
	}
	
	@GetMapping("/jpa/users/{id}/posts")
	public List<Post> retrievePostsOfUser(@PathVariable int id){
		Optional<User> user = userRepository.findById(id);
		if(!user.isPresent()) {
			throw new UserNotFound("id-"+id);
		}
		return user.get().getPosts();
	}
	
	@PostMapping("/jpa/users/{id}/jpa/posts")
	public ResponseEntity createPostOfUser(@PathVariable int id,@RequestBody Post post){
		Optional<User> userOptional = userRepository.findById(id);
		if(!userOptional.isPresent()) {
			throw new UserNotFound("id-"+id);
		}
		
		User user = userOptional.get();
		post.setUser(user);
		postRepository.save(post);
		URI uri = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(post.getId()).toUri();
			EntityModel<Post> model = EntityModel.of(post);
			WebMvcLinkBuilder linkToSavedUser = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PostJpaController.class).getPostById(post.getId()));
			model.add(linkToSavedUser.withRel("saved-post"));
		return ResponseEntity.created(uri).body(model);
	}
}
