package com.fulfilment.application.monolith.stores;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class LegacyStoreManagerGatewayTest {
	
	@Test
	void shouldCreateStoreOnLegacySystem() {
		
		LegacyStoreManagerGateway gateway = new LegacyStoreManagerGateway();
		
		Store store = new Store("LegacyCreateTest");
		store.quantityProductsInStock = 10;
		
		assertDoesNotThrow(() -> gateway.createStoreOnLegacySystem(store));
		
	}
	
	@Test
	void shouldUpdateStoreOnLegacySystem() {
		
		LegacyStoreManagerGateway gateway = new LegacyStoreManagerGateway();
		
		Store store = new Store("LegacyUpdateTest");
		store.quantityProductsInStock = 20;
		
		assertDoesNotThrow(() -> gateway.updateStoreOnLegacySystem(store));
		
	}
	
	@Test
	void shouldHandleExceptionWhileCreatingFile() {
		
		LegacyStoreManagerGateway gateway = new LegacyStoreManagerGateway();
		
		Store store = new Store();
		store.name = null;
		
		gateway.createStoreOnLegacySystem(store);
		
	}
	
	@Test
	void shouldHandleExceptionWhileUpdatingFile() {
		
		LegacyStoreManagerGateway gateway = new LegacyStoreManagerGateway();
		
		Store store = new Store();
		store.name = null;
		
		gateway.updateStoreOnLegacySystem(store);
		
	}

}
