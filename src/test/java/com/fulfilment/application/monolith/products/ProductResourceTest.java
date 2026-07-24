package com.fulfilment.application.monolith.products;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.Test;

import jakarta.ws.rs.WebApplicationException;

public class ProductResourceTest {
	
	@Test
	void shouldGetProducts() throws Exception{
		
		ProductRepository repository = mock(ProductRepository.class);
		
		ProductResource resource = new ProductResource();
		
		Field field = ProductResource.class.getDeclaredField("productRepository");
		field.setAccessible(true);
		field.set(resource, repository);
		
		when(repository.listAll(any())).thenReturn(List.of(new Product("Laptop")));
		
		List<Product> products = resource.get();
		
		assertEquals(1,products.size());
		verify(repository).listAll(any());
		
	}
	
	@Test
	void shouldGetSingleProduct() throws Exception{
		
		Product product = new Product("Laptop");
		product.id = 1L;
		
		ProductRepository repository = mock(ProductRepository.class);
		when(repository.findById(1L)).thenReturn(product);
		
		ProductResource resource = new ProductResource();
		
		Field field = ProductResource.class.getDeclaredField("productRepository");
		field.setAccessible(true);
		field.set(resource, repository);
		
		Product result = resource.getSingle(1L);
			
		assertEquals("Laptop",result.name);

	}
	
	@Test
	void shouldThrowWhenProductNotFound() throws Exception{
		
		ProductRepository repository = mock(ProductRepository.class);
		when(repository.findById(100L)).thenReturn(null);
		
		ProductResource resource = new ProductResource();
		
		Field field = ProductResource.class.getDeclaredField("productRepository");
		field.setAccessible(true);
		field.set(resource, repository);

		assertThrows(WebApplicationException.class,()->resource.getSingle(100L));

	}
	
	@Test
	void shouldCreateProducts() throws Exception{
		
		ProductRepository repository = mock(ProductRepository.class);
		ProductResource resource = new ProductResource();
		
		Field field = ProductResource.class.getDeclaredField("productRepository");
		field.setAccessible(true);
		field.set(resource, repository);
		
		Product product = new Product("Laptop");
		resource.create(product);
		
		verify(repository).persist(product);
		
	}
	
	@Test
	void shouldUpdateProducts() throws Exception{
		
		Product existing = new Product("Old");
		existing.id = 1L;
		
		Product updated = new Product("New");
		updated.description = "Desc";
		updated.price = BigDecimal.TEN;
		updated.stock = 50;
		
		ProductRepository repository = mock(ProductRepository.class);
		when(repository.findById(1L)).thenReturn(existing);
		
		ProductResource resource = new ProductResource();
		
		Field field = ProductResource.class.getDeclaredField("productRepository");
		field.setAccessible(true);
		field.set(resource, repository);
		
		Product result = resource.update(1L, updated);
		
		assertEquals("New",result.name);
		assertEquals("Desc",result.description);
		assertEquals(BigDecimal.TEN,result.price);
		assertEquals(50,result.stock);

		
		verify(repository).persist(existing);
		
	}
	
	@Test
	void shouldDeleteProducts() throws Exception{
		
		Product product = new Product("Laptop");
		
		ProductRepository repository = mock(ProductRepository.class);
		when(repository.findById(1L)).thenReturn(product);
		
		ProductResource resource = new ProductResource();
		
		Field field = ProductResource.class.getDeclaredField("productRepository");
		field.setAccessible(true);
		field.set(resource, repository);
		
		resource.delete(1L);
		
		verify(repository).delete(product);
		
	}
	
	@Test
	void shouldThrowWhenDeletingMissingProduct() throws Exception{
		
		ProductRepository repository = mock(ProductRepository.class);
		when(repository.findById(1L)).thenReturn(null);
		
		ProductResource resource = new ProductResource();
		
		Field field = ProductResource.class.getDeclaredField("productRepository");
		field.setAccessible(true);
		field.set(resource, repository);
				
		
		assertThrows(WebApplicationException.class,()->resource.delete(1L));
		
	}
	
	@Test
	void shouldThrowWhenUpdatingMissingProduct() throws Exception{
		
		ProductRepository repository = mock(ProductRepository.class);
		when(repository.findById(999L)).thenReturn(null);
		
		ProductResource resource = new ProductResource();
		
		Field field = ProductResource.class.getDeclaredField("productRepository");
		field.setAccessible(true);
		field.set(resource, repository);
		
		
		Product product = new Product("Laptop");
		
		assertThrows(WebApplicationException.class,()->resource.update(999L, product));

	}

}
