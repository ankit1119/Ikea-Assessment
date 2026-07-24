package com.fulfilment.application.monolith.warehouses.adapters.database;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import com.fulfilment.application.monolith.warehouses.domain.models.Warehouse;

public class DbWarehouseTest {
	
	@Test
	void shouldConvertToWarehouse() {
		
		DbWarehouse dbWarehouse = new DbWarehouse();
		
		dbWarehouse.businessUnitCode = "BU001";
		dbWarehouse.location = "Noida";
		dbWarehouse.capacity = 100;
		dbWarehouse.stock = 50;
		dbWarehouse.createdAt = LocalDateTime.now();
		dbWarehouse.archivedAt = LocalDateTime.now().plusDays(1);
		
		Warehouse warehouse = dbWarehouse.toWarehouse();
		
		assertNotNull(warehouse);
		assertEquals("BU001",warehouse.businessUnitCode);
		assertEquals("Noida",warehouse.location);
		assertEquals(100,warehouse.capacity);
		assertEquals(50,warehouse.stock);
		assertEquals(dbWarehouse.createdAt,warehouse.createdAt);
		assertEquals(dbWarehouse.archivedAt,warehouse.archivedAt);

	}
	
	@Test
	void shouldCreateDbWarehouse() {
		
		DbWarehouse dbWarehouse = new DbWarehouse();
		
		assertNotNull(dbWarehouse);
		
	}

}
