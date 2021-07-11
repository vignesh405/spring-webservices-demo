package com.webservices.demo.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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
import com.webservices.demo.model.User;
import com.webservices.demo.repository.UserDAOService;



@RestController
public class UserController {

	@Autowired
	private UserDAOService userDAOService;
	
	@Autowired
	private MessageSource messageSource;
	
	@GetMapping("/users")
	public MappingJacksonValue retrieveAll(){
		SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("id","name","birthDate","secretInfo2","openInfo");
		FilterProvider filters = new SimpleFilterProvider().addFilter("UserFilter", filter);
		List<User> userList = userDAOService.findAll();
//		ArrayList<MappingJacksonValue> mappingList = new ArrayList<>();
//		for (User user : userList) {
//			MappingJacksonValue mapping = new MappingJacksonValue(user);
//			mapping.setFilters(filters);
//			mappingList.add(mapping);
//		}
		MappingJacksonValue mapping = new MappingJacksonValue(userList);
		mapping.setFilters(filters);
		return mapping;
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
	
	@GetMapping("/users/about")
	public String aboutUsers() {
		
		return messageSource.getMessage("about.users.message", null, "Default Message", LocaleContextHolder.getLocale());
	}
}
