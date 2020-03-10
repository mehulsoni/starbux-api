package com.starbux.customer.attribute.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.starbux.TestTag;
import com.starbux.configuration.ApiIntegrationTest;
import com.starbux.dto.response.ProductResDto;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Tag(TestTag.INTEGRATION_TEST)
@ApiIntegrationTest
@ActiveProfiles("INTEGRATIONTEST")
@Sql(scripts = {
		"/data/sql/cleanup.sql",
		"/data/sql/initial_setup.sql"
})
class ProductControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	void getProduct_byId() throws Exception {
		MvcResult mvcResult =
				mockMvc.perform(
						get("/v1/product/{id}",
								1))
						.andExpect(status().isOk()).andReturn();
		ProductResDto productResDto =
				objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
						new TypeReference<>() {
						});
		assertEquals(1, productResDto.getId());
	}

	@Test
	void get_all_Product() throws Exception {
		MvcResult mvcResult =
				mockMvc.perform(
						get("/v1/product/",
								1))
						.andExpect(status().isOk()).andReturn();
		List<ProductResDto> productResDto =
				objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
						new TypeReference<>() {
						});
		assertEquals(4, productResDto.size());
	}

}
