package com.webservices.demo.filters;

import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

@Component
public class FilterHelper {
	private static SimpleBeanPropertyFilter userFilter;
	private static FilterProvider userFilters;
	
	
	private MappingJacksonValue mapping;
	
	static {
		userFilter = SimpleBeanPropertyFilter.filterOutAllExcept("id","name","birthDate","secretInfo2","openInfo");
		userFilters = new SimpleFilterProvider().addFilter("UserFilter", userFilter);
	}
	

	public MappingJacksonValue getUserMapping(Object user) {
		mapping = new MappingJacksonValue(user);
		mapping.setFilters(userFilters);
		return mapping;
	}
	

	
	
}
