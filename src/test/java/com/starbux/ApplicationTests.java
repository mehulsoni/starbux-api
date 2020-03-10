package com.starbux;

import com.starbux.configuration.ApiIntegrationTest;

import org.junit.Assert;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;

@Tag(TestTag.INTEGRATION_TEST)
@ApiIntegrationTest
@ActiveProfiles("INTEGRATIONTEST")
public class ApplicationTests {

	@Autowired
	ApplicationContext context;

	@Test
	public void contextLoads() {
		Assert.assertNotNull(context);
	}
}
