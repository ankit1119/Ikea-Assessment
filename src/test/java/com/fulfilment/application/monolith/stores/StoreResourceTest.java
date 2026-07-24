package com.fulfilment.application.monolith.stores;

import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;
import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class StoreResourceTest {
	
	
	@Test
	void shouldGetStores() {
		
		given().when().get("/store").then().statusCode(200);
	}
	
	@Test
	void shouldCreateStores() {
		
		String body = """
				{
				"name":"Test Store",
				"quantityProductsInStock":10
				}
				""";
		given().contentType("application/json").body(body).when().post("/store").then().statusCode(201).body("id", notNullValue());
	}

	@Test
	void shouldRejectCreateWhenIdProvided() {
		
		String body = """
				{
				"name":"Test Store",
				"quantityProductsInStock":10
				}
				""";
		given().contentType("application/json").body(body).when().post("/store").then().statusCode(201).body("id", notNullValue());
	}
}
