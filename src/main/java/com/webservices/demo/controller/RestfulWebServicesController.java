package com.webservices.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.webservices.demo.model.HelloWorldBean;

@RestController
public class RestfulWebServicesController {
	@GetMapping(path="/hello-world")
	public String helloWorld() {
		return "Hello world";
	}
	
	@GetMapping(path="/hello-world-bean")
	public HelloWorldBean helloWorldBean() {
		return new HelloWorldBean("Hello world bean");
	}
	
	@GetMapping(path="/hello-world-bean/{name}")
	public HelloWorldBean helloWorldBeanName(@PathVariable String name) {
		return new HelloWorldBean(String.format("Hello World %s", name));
	}
}
