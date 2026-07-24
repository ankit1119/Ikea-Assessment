package com.fulfilment.application.monolith.products;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

public class ProductRepositoryTest {
	
	@Test
	void shouldCreateRepository() {
		ProductRepository repository = new ProductRepository();
		
		assertNotNull(repository);
	}

}
