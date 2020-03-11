package com.starbux.configuration;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.support.TestPropertySourceUtils;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;

@SuppressWarnings(value = {"AbbreviationAsWordInName"})
@Configuration
@Profile(value = {"INTEGRATIONTEST"})
public class DataSourceConfiguration {

	public static class Initializer
			implements ApplicationContextInitializer<ConfigurableApplicationContext> {

		@Container
		PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer();

		/**
		 * Inner class for initializing application context.
		 *
		 * configurableApplicationContext {@link ConfigurableApplicationContext}.
		 */
		public void initialize(ConfigurableApplicationContext configurableApplicationContext) {

			postgreSQLContainer.start();
			TestPropertySourceUtils.addInlinedPropertiesToEnvironment(
					configurableApplicationContext,
					"spring.datasource.url=" + postgreSQLContainer.getJdbcUrl(),
					"spring.datasource.username=" + postgreSQLContainer.getUsername(),
					"spring.datasource.password=" + postgreSQLContainer.getPassword()
			);
		}
	}
}

