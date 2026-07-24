package com.fulfilment.application.monolith.products;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class ProductTest {
	
	@Test
	void shouldCreateEmptyProduct() {
		
		Product product = new Product();
		assertNotNull(product);
		
	}
	
	@Test
	void shouldCreateProductWithName() {
		
		Product product = new Product("Laptop");
		assertEquals("Laptop", product.name);
	}

}
