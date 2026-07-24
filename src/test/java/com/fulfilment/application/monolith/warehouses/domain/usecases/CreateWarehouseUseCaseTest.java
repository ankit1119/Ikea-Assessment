package com.fulfilment.application.monolith.warehouses.domain.usecases;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import com.fulfilment.application.monolith.warehouses.domain.models.Location;
import com.fulfilment.application.monolith.warehouses.domain.models.Warehouse;
import com.fulfilment.application.monolith.warehouses.domain.ports.LocationResolver;
import com.fulfilment.application.monolith.warehouses.domain.ports.WarehouseStore;

public class CreateWarehouseUseCaseTest {
	
	@Test
	void shouldCreateWarehouseSuccessfully() {
		
		WarehouseStore store = mock(WarehouseStore.class);
		LocationResolver resolver = mock(LocationResolver.class);
		
		when(store.findByBusinessUnitCode("BU001")).thenReturn(null);
		
		Location location = new Location("AMSTERDAM-001", 10, 100);
		
		when(resolver.resolveByIdentifier("AMSTERDAM-001")).thenReturn(location);
		
		CreateWarehouseUseCase useCase = new CreateWarehouseUseCase(store, resolver);
		
		Warehouse warehouse = new Warehouse();
		warehouse.businessUnitCode = "BU001";
		warehouse.location = "AMSTERDAM-001";
		warehouse.capacity = 50;
		warehouse.stock = 20;
		
		useCase.create(warehouse);
		assertNotNull(warehouse.createdAt);
		verify(store).create(warehouse);
		
	}
	
	@Test
	void shouldThrowWhenWarehouseAlreadyExists() {
		
		WarehouseStore store = mock(WarehouseStore.class);
		LocationResolver resolver = mock(LocationResolver.class);
		
		Warehouse existing = new Warehouse();
		
		when(store.findByBusinessUnitCode("BU001")).thenReturn(existing);
		
		CreateWarehouseUseCase useCase = new CreateWarehouseUseCase(store, resolver);
		
		Warehouse warehouse = new Warehouse();
		warehouse.businessUnitCode = "BU001";

		assertThrows(IllegalArgumentException.class,()->useCase.create(warehouse));
		
	}
	
	@Test
	void shouldThrowWhenLocationIsInvalid() {
		
		WarehouseStore store = mock(WarehouseStore.class);
		LocationResolver resolver = mock(LocationResolver.class);
		
		when(store.findByBusinessUnitCode("BU001")).thenReturn(null);
		when(resolver.resolveByIdentifier("INVALID")).thenReturn(null);
				
		CreateWarehouseUseCase useCase = new CreateWarehouseUseCase(store, resolver);
		
		Warehouse warehouse = new Warehouse();
		warehouse.businessUnitCode = "BU001";
		warehouse.location = "INVALID";
		
		assertThrows(IllegalArgumentException.class,()->useCase.create(warehouse));
		
	}
	
}
