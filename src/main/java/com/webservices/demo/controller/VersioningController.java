//package com.webservices.demo.controller;
//
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.webservices.demo.model.Name;
//import com.webservices.demo.model.PersonV1;
//import com.webservices.demo.model.PersonV2;
//
//@RestController
//public class VersioningController {
//
//	@GetMapping("v1/person")
//	public PersonV1 personV1() {
//		return new PersonV1("Vignesh Ramamurthy");
//	}
//	
//	@GetMapping("v2/person")
//	public PersonV2 personV2() {
//		return new PersonV2(new Name("Vignesh","Ramamurthy"));
//	}
//	
//	@GetMapping(value="/person/param",params="version=1")
//	public PersonV1 personV1Param() {
//		return new PersonV1("Vignesh Ramamurthy");
//	}
//	
//	@GetMapping(value="/person/param",params="version=2")
//	public PersonV2 personV2Param() {
//		return new PersonV2(new Name("Vignesh","Ramamurthy"));
//	}
//	
//	@GetMapping(value="/person/header",headers="X-API-VERSION=1")
//	public PersonV1 personV1Header() {
//		return new PersonV1("Vignesh Ramamurthy");
//	}
//	
//	@GetMapping(value="/person/header",headers="X-API-VERSION=2")
//	public PersonV2 personV2Header() {
//		return new PersonV2(new Name("Vignesh","Ramamurthy"));
//	}
//	
//	@GetMapping(value="/person/produces",produces="application/v1+json")
//	public PersonV1 personV1Produces() {
//		return new PersonV1("Vignesh Ramamurthy");
//	}
//	
//	@GetMapping(value="/person/produces",produces="application/v2+json")
//	public PersonV2 personV2Produces() {
//		return new PersonV2(new Name("Vignesh","Ramamurthy"));
//	}
//}
