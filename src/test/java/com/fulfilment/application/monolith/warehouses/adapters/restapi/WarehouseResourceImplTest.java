package com.fulfilment.application.monolith.warehouses.adapters.restapi;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.lang.reflect.Field;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.fulfilment.application.monolith.warehouses.adapters.database.WarehouseRepository;
import com.fulfilment.application.monolith.warehouses.domain.models.Warehouse;
import com.fulfilment.application.monolith.warehouses.domain.ports.ArchiveWarehouseOperation;
import com.fulfilment.application.monolith.warehouses.domain.ports.CreateWarehouseOperation;
import com.fulfilment.application.monolith.warehouses.domain.ports.ReplaceWarehouseOperation;

import jakarta.ws.rs.WebApplicationException;




public class WarehouseResourceImplTest {
	
	private WarehouseResourceImpl createResource(
			WarehouseRepository repository,
			CreateWarehouseOperation createOp,
			ArchiveWarehouseOperation archiveOp,
			ReplaceWarehouseOperation replaceOp) throws Exception{
		
		WarehouseResourceImpl resource = new WarehouseResourceImpl();
		
		Field f1 = WarehouseResourceImpl.class.getDeclaredField("warehouseRepository");
		f1.setAccessible(true);
		f1.set(resource, repository);
		
		Field f2 = WarehouseResourceImpl.class.getDeclaredField("createWarehouseOperation");
		f2.setAccessible(true);
		f2.set(resource, createOp);
		
		Field f3 = WarehouseResourceImpl.class.getDeclaredField("archiveWarehouseOperation");
		f3.setAccessible(true);
		f3.set(resource, archiveOp);
		
		Field f4 = WarehouseResourceImpl.class.getDeclaredField("replaceWarehouseOperation");
		f4.setAccessible(true);
		f4.set(resource, replaceOp);
		
		return resource;
		
	}
	
	@Test
	void shouldListAllWarehouse() throws Exception{
		
		WarehouseRepository repository = mock(WarehouseRepository.class);
		
		Warehouse domainWarehouse = new Warehouse();
		
		domainWarehouse.businessUnitCode = "BU001";
		domainWarehouse.location = "Noida";
		domainWarehouse.capacity = 100;
		domainWarehouse.stock = 50;
		
		when(repository.getAll()).thenReturn(List.of(domainWarehouse));
		
		WarehouseResourceImpl resource = createResource(repository, null, null, null);
		
		List<com.warehouse.api.beans.Warehouse> result = resource.listAllWarehousesUnits();
		
		assertEquals(1, result.size());
		assertEquals("BU001", result.get(0).getBusinessUnitCode());
		
	}
	
	@Test
	void shouldCreateWarehouse() throws Exception{
		
		CreateWarehouseOperation createOp = mock(CreateWarehouseOperation.class);
				
		WarehouseResourceImpl resource = createResource(
											mock(WarehouseRepository.class),
											createOp,
											mock(ArchiveWarehouseOperation.class),
											mock(ReplaceWarehouseOperation.class));
		
		com.warehouse.api.beans.Warehouse request = new com.warehouse.api.beans.Warehouse();
		
		request.setBusinessUnitCode("BU001");
		request.setLocation("Noida");
		request.setCapacity(100);
		request.setStock(20);
		
		com.warehouse.api.beans.Warehouse response = resource.createANewWarehouseUnit(request);
		
		assertEquals("BU001", response.getBusinessUnitCode());
		verify(createOp).create(any());
		
	}
	
	@Test
	void shouldThrowWhenCreateFails() throws Exception{
		
		CreateWarehouseOperation createOp = mock(CreateWarehouseOperation.class);
		
		doThrow(new IllegalArgumentException("Invalid Warehouse")).when(createOp).create(any());
				
		WarehouseResourceImpl resource = createResource(
											mock(WarehouseRepository.class),
											createOp,
											mock(ArchiveWarehouseOperation.class),
											mock(ReplaceWarehouseOperation.class));
		
		com.warehouse.api.beans.Warehouse request = new com.warehouse.api.beans.Warehouse();
		
		assertThrows(WebApplicationException.class, ()->resource.createANewWarehouseUnit(request));
		
	}
	
	@Test
	void shouldGetWarehouseById() throws Exception{
		
		WarehouseRepository repository = mock(WarehouseRepository.class);
		
		Warehouse domainWarehouse = new Warehouse();
		
		domainWarehouse.businessUnitCode = "BU001";	

		when(repository.findByBusinessUnitCode("BU001")).thenReturn(domainWarehouse);
		
		WarehouseResourceImpl resource = createResource(repository, null, null, null);
		
		
		assertNotNull(resource.getAWarehouseUnitByID("BU001"));

}
	
	@Test
	void shouldThrowWhenWarehouseNotFound() throws Exception{
		
		WarehouseRepository repository = mock(WarehouseRepository.class);
		
		when(repository.findByBusinessUnitCode("UNKNOWN")).thenReturn(null);
		
		WarehouseResourceImpl resource = createResource(repository, null, null, null);
		
		assertThrows(WebApplicationException.class, ()->resource.getAWarehouseUnitByID("UNKNOWN"));


	}
	
	@Test
	void shouldArchiveWarehouse() throws Exception{
		
		WarehouseRepository repository = mock(WarehouseRepository.class);
		
		ArchiveWarehouseOperation archiveOp = mock(ArchiveWarehouseOperation.class);
		
		Warehouse domainWarehouse = new Warehouse();
		domainWarehouse.businessUnitCode = "BU001";	

		when(repository.findByBusinessUnitCode("BU001")).thenReturn(domainWarehouse);
		
		WarehouseResourceImpl resource = createResource(
											repository,
											mock(CreateWarehouseOperation.class),
											archiveOp,
											mock(ReplaceWarehouseOperation.class));
		
	resource.archiveAWarehouseUnitByID("BU001");
	verify(archiveOp).archive(any());
		
	}
	
	@Test
	void shouldThrowWhenArchiveWarehouseNotFound() throws Exception{
		
		WarehouseRepository repository = mock(WarehouseRepository.class);
		
		when(repository.findByBusinessUnitCode("UNKNOWN")).thenReturn(null);
		
		
		WarehouseResourceImpl resource = createResource(
											repository,
											mock(CreateWarehouseOperation.class),
											mock(ArchiveWarehouseOperation.class),
											mock(ReplaceWarehouseOperation.class));
		
		assertThrows(WebApplicationException.class, ()->resource.getAWarehouseUnitByID("UNKNOWN"));
		
	}
	
	@Test
	void shouldReplaceWarehouse() throws Exception{
		
		WarehouseRepository repository = mock(WarehouseRepository.class);
		
		ReplaceWarehouseOperation replaceOp = mock(ReplaceWarehouseOperation.class);
		
		Warehouse warehouse = new Warehouse();
		warehouse.businessUnitCode = "BU001";	

		when(repository.findByBusinessUnitCode("BU001")).thenReturn(warehouse);
		
		WarehouseResourceImpl resource = createResource(
											repository,
											mock(CreateWarehouseOperation.class),
											mock(ArchiveWarehouseOperation.class),
											replaceOp);
		
		com.warehouse.api.beans.Warehouse request = new com.warehouse.api.beans.Warehouse();
		
		request.setLocation("Noida");
		request.setCapacity(100);
		request.setStock(50);
				
		assertNotNull(resource.replaceTheCurrentActiveWarehouse("BU001", request));
		verify(replaceOp).replace(any());
		
	}
	
	@Test
	void shouldThrowWhenReplaceFails() throws Exception{
		
		ReplaceWarehouseOperation replaceOp = mock(ReplaceWarehouseOperation.class);
		
		doThrow(new IllegalArgumentException("Invalid")).when(replaceOp).replace(any());
		
		WarehouseResourceImpl resource = createResource(
				mock(WarehouseRepository.class),
				mock(CreateWarehouseOperation.class),
				mock(ArchiveWarehouseOperation.class),
				replaceOp);
		
		com.warehouse.api.beans.Warehouse request = new com.warehouse.api.beans.Warehouse();
		
		assertThrows(WebApplicationException.class, ()->resource.replaceTheCurrentActiveWarehouse("BU001",request));

}
	
}

