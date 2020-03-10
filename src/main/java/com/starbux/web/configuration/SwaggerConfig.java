package com.starbux.web.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;

// TODO: Read these values from applicaton.yml file
@OpenAPIDefinition(
		info = @Info(
				title = "starbux-api",
				version = "v1",
				description = "starbux-api, REST API",
				license = @License(name = "Starbux", url = "www.starbux.com"),
				contact = @Contact(email = "dev@starbux.com")
		)
)
public class SwaggerConfig {

}
