package com.fulfilment.application.monolith.stores;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class StoreTest {
	
	@Test
	void shouldCreateEmptyStore() {
		
		Store store = new Store();
		
		assertNotNull(store);
	}
	
	@Test
	void shouldCreateStoreWithName() {
		
		Store store = new Store("New Store");
		
		assertEquals("New Store",store.name);
		
	}

}
