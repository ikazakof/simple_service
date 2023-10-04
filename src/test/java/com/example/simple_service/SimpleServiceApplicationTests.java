package com.example.simple_service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SimpleServiceApplicationTests {

	private final SimpleServiceApplication simpleServiceApplication;
	@Autowired
	public SimpleServiceApplicationTests(SimpleServiceApplication simpleServiceApplication) {
		this.simpleServiceApplication = simpleServiceApplication;
	}
	@Test
	void contextLoads() {
		Assertions.assertNotNull(simpleServiceApplication);
	}

}
