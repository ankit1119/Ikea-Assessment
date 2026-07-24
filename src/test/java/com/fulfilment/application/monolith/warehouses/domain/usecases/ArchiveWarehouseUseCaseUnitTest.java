package com.fulfilment.application.monolith.warehouses.domain.usecases;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import com.fulfilment.application.monolith.warehouses.domain.models.Warehouse;
import com.fulfilment.application.monolith.warehouses.domain.ports.WarehouseStore;

public class ArchiveWarehouseUseCaseUnitTest {
	
	@Test
	void shouldArchiveWarehouse() {
		
		WarehouseStore store = mock(WarehouseStore.class);
		
		Warehouse existing = new Warehouse();
		existing.businessUnitCode = "BU001";
		
		when(store.findByBusinessUnitCode("BU001")).thenReturn(existing);
		
		ArchiveWarehouseUseCase useCase = new ArchiveWarehouseUseCase(store);
		
		Warehouse warehouse = new Warehouse();
		warehouse.businessUnitCode = "BU001";
		
		useCase.archive(warehouse);
		assertNotNull(existing.archivedAt);
		verify(store).update(existing);
	}
	
	@Test
	void shoulThrowWhenWarehouseDoesNotExist() {
		
		WarehouseStore store = mock(WarehouseStore.class);
		
		when(store.findByBusinessUnitCode("BU001")).thenReturn(null);

		ArchiveWarehouseUseCase useCase = new ArchiveWarehouseUseCase(store);
		
		Warehouse warehouse = new Warehouse();
		warehouse.businessUnitCode = "BU001";
		
		assertThrows(IllegalArgumentException.class,()->useCase.archive(warehouse));
	}
	
	@Test
	void shoulThrowWhenWarehouseAlreadyArchived() {
		
		WarehouseStore store = mock(WarehouseStore.class);
		
		Warehouse existing = new Warehouse();
		existing.businessUnitCode = "BU001";
		existing.archivedAt = LocalDateTime.now();
		
		when(store.findByBusinessUnitCode("BU001")).thenReturn(existing);
		
		ArchiveWarehouseUseCase useCase = new ArchiveWarehouseUseCase(store);
		
		Warehouse warehouse = new Warehouse();
		warehouse.businessUnitCode = "BU001";
		
		assertThrows(IllegalArgumentException.class,()->useCase.archive(warehouse));
	}

}
