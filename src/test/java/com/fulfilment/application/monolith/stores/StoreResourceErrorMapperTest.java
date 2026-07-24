package com.fulfilment.application.monolith.stores;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.reflect.Field;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;

public class StoreResourceErrorMapperTest {
	
	@Test
	void shouldHandleGenericException() throws Exception{
		
		StoreResource.ErrorMapper mapper = new StoreResource.ErrorMapper();
		
		Field field = StoreResource.ErrorMapper.class.getDeclaredField("objectMapper");
		field.setAccessible(true);
		field.set(mapper, new ObjectMapper());
		
		Response response = mapper.toResponse(new RuntimeException("Something Went Wrong"));
		
		assertEquals(500, response.getStatus());
		
	}
	
	@Test
	void shouldHandleWebApplicationException() throws Exception{
		
		StoreResource.ErrorMapper mapper = new StoreResource.ErrorMapper();
		
		Field field = StoreResource.ErrorMapper.class.getDeclaredField("objectMapper");
		field.setAccessible(true);
		field.set(mapper, new ObjectMapper());
		
		Response response = mapper.toResponse(new WebApplicationException("Bad Request", 400));
		
		assertEquals(400, response.getStatus());
		
	}
	
	@Test
	void shouldHandleExceptionWithoutMessage() throws Exception{
		
		StoreResource.ErrorMapper mapper = new StoreResource.ErrorMapper();
		
		Field field = StoreResource.ErrorMapper.class.getDeclaredField("objectMapper");
		field.setAccessible(true);
		field.set(mapper, new ObjectMapper());
		
		Response response = mapper.toResponse(new RuntimeException());
		
		assertEquals(500, response.getStatus());
		
	}

}
