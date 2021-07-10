package com.webservices.demo.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.webservices.demo.exception.UserNotFound;
import com.webservices.demo.model.User;
import com.webservices.demo.repository.UserDAOService;



@RestController
public class UserController {

	@Autowired
	private UserDAOService userDAOService;
	
	@GetMapping("/users")
	public List<User> retrieveAll(){
		return userDAOService.findAll();
	}
	
	@GetMapping("/users/{id}")
	public EntityModel<User> retrieveUser(@PathVariable int id) {
		User user = userDAOService.findOne(id);
		if(user==null)
			throw new UserNotFound("id-"+id);
		
		EntityModel<User> model = EntityModel.of(user);
		WebMvcLinkBuilder linkToUsers = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).retrieveAll());
		model.add(linkToUsers.withRel("all-users"));
		return model;
	}
	
	@PostMapping("/users")
	public ResponseEntity postUser(@Valid @RequestBody User user) {
		User savedUser = userDAOService.save(user);
		URI uri = ServletUriComponentsBuilder
			.fromCurrentRequest()
			.path("/{id}")
			.buildAndExpand(savedUser.getId()).toUri();
		EntityModel<User> model = EntityModel.of(savedUser);
		WebMvcLinkBuilder linkToSavedUser = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).retrieveUser(savedUser.getId()));
		model.add(linkToSavedUser.withRel("saved-user"));
		return ResponseEntity.created(uri).body(model);
	}
	
	@DeleteMapping("/users/{id}")
	public void deleteUser(@PathVariable int id) {
		if(!userDAOService.deleteById(id)) {
			throw new UserNotFound("id-"+id);
		}
	}
}
