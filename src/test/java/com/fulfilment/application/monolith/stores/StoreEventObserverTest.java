package com.fulfilment.application.monolith.stores;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.lang.reflect.Field;

 
public class StoreEventObserverTest {

  @Test
  void shouldHandleStoreCreatedEvent() throws Exception {
	  
	  StoreEventObserver observer = new StoreEventObserver();
	  
	  LegacyStoreManagerGateway gateway = mock(LegacyStoreManagerGateway.class);
	  
	  Field field = StoreEventObserver.class.getDeclaredField("legacyStoreManagerGateway");
	  
	  field.setAccessible(true);
	  field.set(observer, gateway);
	  
	  Store store = new Store("Test store");
	  store.id = 1L;
	  
	  StoreCreatedEvent event = new StoreCreatedEvent(store);
	  
	  observer.onStoreCreated(event);
	  verify(gateway).createStoreOnLegacySystem(store);
	  
	  
  }
  
  @Test
  void shouldHandleStoreUpdatedEvent() throws Exception {
	  
	  StoreEventObserver observer = new StoreEventObserver();
	  
	  LegacyStoreManagerGateway gateway = mock(LegacyStoreManagerGateway.class);
	  
	  Field field = StoreEventObserver.class.getDeclaredField("legacyStoreManagerGateway");
	  
	  field.setAccessible(true);
	  field.set(observer, gateway);
	  
	  Store store = new Store("Updated store");
	  store.id = 2L;
	  
	  StoreUpdatedEvent event = new StoreUpdatedEvent(store);
	  
	  observer.onStoreUpdated(event);
	  verify(gateway).updateStoreOnLegacySystem(store);
	  
	  
  }
}
