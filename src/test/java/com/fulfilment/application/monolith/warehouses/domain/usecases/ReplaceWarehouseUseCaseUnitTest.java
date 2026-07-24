package com.fulfilment.application.monolith.warehouses.domain.usecases;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import com.fulfilment.application.monolith.warehouses.domain.models.Location;
import com.fulfilment.application.monolith.warehouses.domain.models.Warehouse;
import com.fulfilment.application.monolith.warehouses.domain.ports.LocationResolver;
import com.fulfilment.application.monolith.warehouses.domain.ports.WarehouseStore;

public class ReplaceWarehouseUseCaseUnitTest {
	
	@Test
	void shouldReplaceWarehouseSuccessfully() {
		
		WarehouseStore store = mock(WarehouseStore.class);
		LocationResolver resolver = mock(LocationResolver.class);
		
		Warehouse existing = new Warehouse();
		existing.businessUnitCode = "BU001";
		
		when(store.findByBusinessUnitCode("BU001")).thenReturn(existing);
		
		Location location = new Location("AMSTERDAM-001", 10, 100);
		
		when(resolver.resolveByIdentifier("AMSTERDAM-001")).thenReturn(location);
		
		ReplaceWarehouseUseCase useCase = new ReplaceWarehouseUseCase(store, resolver);
		
		Warehouse newwarehouse = new Warehouse();
		newwarehouse.businessUnitCode = "BU001";
		newwarehouse.location = "AMSTERDAM-001";
		newwarehouse.capacity = 50;
		newwarehouse.stock = 20;
		
		useCase.replace(newwarehouse);

		assertEquals("AMSTERDAM-001",existing.location);
		assertEquals(50,existing.capacity);
		assertEquals(20,existing.stock);
		
		verify(store).update(existing);
		
	}
	
	@Test
	void shouldThrowWhenWarehouseAlreadyArchived() {
		
		WarehouseStore store = mock(WarehouseStore.class);
		LocationResolver resolver = mock(LocationResolver.class);
		
		Warehouse existing = new Warehouse();
		existing.businessUnitCode = "BU001";
		existing.archivedAt = LocalDateTime.now();
		
		when(store.findByBusinessUnitCode("BU001")).thenReturn(existing);
		
		ReplaceWarehouseUseCase useCase = new ReplaceWarehouseUseCase(store, resolver);
		
		Warehouse warehouse = new Warehouse();
		warehouse.businessUnitCode = "BU001";
		
		assertThrows(IllegalArgumentException.class,()->useCase.replace(warehouse));
		
	}
	
	@Test
	void shouldThrowWhenWarehouseDoesNotExist() {
		
		WarehouseStore store = mock(WarehouseStore.class);
		LocationResolver resolver = mock(LocationResolver.class);
				
		when(store.findByBusinessUnitCode("BU001")).thenReturn(null);
		
		ReplaceWarehouseUseCase useCase = new ReplaceWarehouseUseCase(store, resolver);
		
		Warehouse warehouse = new Warehouse();
		warehouse.businessUnitCode = "BU001";
		
		assertThrows(IllegalArgumentException.class,()->useCase.replace(warehouse));
		
	}
	
	@Test
	void shouldThrowWhenLocationIsIncalid() {
		
		WarehouseStore store = mock(WarehouseStore.class);
		LocationResolver resolver = mock(LocationResolver.class);
		
		Warehouse existing = new Warehouse();
		existing.businessUnitCode = "BU001";
		
		when(store.findByBusinessUnitCode("BU001")).thenReturn(existing);
		when(resolver.resolveByIdentifier("INVALID")).thenReturn(null);
		
		ReplaceWarehouseUseCase useCase = new ReplaceWarehouseUseCase(store, resolver);
		
		Warehouse warehouse = new Warehouse();
		warehouse.businessUnitCode = "BU001";
		warehouse.location = "INVALID";
		
		assertThrows(IllegalArgumentException.class,()->useCase.replace(warehouse));
		
	}
	
	@Test
	void shouldThrowWhenCapacityExceeddLocationCapacity() {
		
		WarehouseStore store = mock(WarehouseStore.class);
		LocationResolver resolver = mock(LocationResolver.class);
		
		Warehouse existing = new Warehouse();
		existing.businessUnitCode = "BU001";
		
		when(store.findByBusinessUnitCode("BU001")).thenReturn(existing);
		
		Location location = new Location("AMSTERDAM-001", 10, 50);
		
		when(resolver.resolveByIdentifier("BU001")).thenReturn(location);
		
		ReplaceWarehouseUseCase useCase = new ReplaceWarehouseUseCase(store, resolver);
		
		Warehouse warehouse = new Warehouse();
		warehouse.businessUnitCode = "BU001";
		warehouse.location = "AMSTERDAM-001";
		warehouse.capacity = 100;
		warehouse.stock = 10;

		assertThrows(IllegalArgumentException.class,()->useCase.replace(warehouse));
		
	}
	
	@Test
	void shouldThrowWhenStockExceedsCapacity() {
		
		WarehouseStore store = mock(WarehouseStore.class);
		LocationResolver resolver = mock(LocationResolver.class);
		
		Warehouse existing = new Warehouse();
		existing.businessUnitCode = "BU001";
		
		when(store.findByBusinessUnitCode("BU001")).thenReturn(existing);
		
		Location location = new Location("AMSTERDAM-001", 10, 100);
		
		when(resolver.resolveByIdentifier("AMSTERDAM-001")).thenReturn(location);

		
		ReplaceWarehouseUseCase useCase = new ReplaceWarehouseUseCase(store, resolver);
		
		Warehouse warehouse = new Warehouse();
		warehouse.businessUnitCode = "BU001";
		warehouse.location = "AMSTERDAM-001";
		warehouse.capacity = 50;
		warehouse.stock = 60;
		

		assertThrows(IllegalArgumentException.class,()->useCase.replace(warehouse));
		
	}
}
