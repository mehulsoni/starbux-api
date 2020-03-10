package com.starbux.customer.attribute.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.starbux.TestTag;
import com.starbux.configuration.ApiIntegrationTest;
import com.starbux.dto.request.ProductReqDto;
import com.starbux.dto.response.ProductResDto;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Tag(TestTag.INTEGRATION_TEST)
@ApiIntegrationTest
@ActiveProfiles("INTEGRATIONTEST")
class ProductControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	@Sql(scripts = {
			"/data/sql/cleanup.sql",
			"/data/sql/initial_setup.sql",
			"/data/sql/initial_data.sql"
	})
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
	@Sql(scripts = {
			"/data/sql/cleanup.sql",
			"/data/sql/initial_setup.sql",
			"/data/sql/initial_data.sql"
	})
	void getProduct_byInvalidId_throwException() throws Exception {
		mockMvc.perform(
				get("/v1/product/{id}",
						10))
				.andExpect(status().isNotFound()).andReturn();

	}

	@Test
	@Sql(scripts = {
			"/data/sql/cleanup.sql",
			"/data/sql/initial_setup.sql",
			"/data/sql/initial_data.sql"
	})
	void get_All_Product() throws Exception {
		MvcResult mvcResult =
				mockMvc.perform(
						get("/v1/product/"))
						.andExpect(status().isOk()).andReturn();
		List<ProductResDto> productResDto =
				objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
						new TypeReference<>() {
						});
		assertEquals(4, productResDto.size());
	}

	@Test
	@Sql(scripts = {
			"/data/sql/cleanup.sql",
			"/data/sql/initial_setup.sql"
	})
	void get_All_Product_WhenNoData_throwException() throws Exception {
		mockMvc.perform(
				get("/v1/product/"))
				.andExpect(status().isNotFound()).andReturn();
	}

	@WithMockUser(roles = {"ADMIN"})
	@Test
	@Sql(scripts = {
			"/data/sql/cleanup.sql",
			"/data/sql/initial_setup.sql"
	})
	void create_NewProduct() throws Exception {
		ProductReqDto productReqDto = new ProductReqDto();
		productReqDto.setCode("TEST");
		productReqDto.setEnabled(true);
		productReqDto.setName("Test");
		productReqDto.setPrice(0.01);
		MvcResult mvcResult =
				mockMvc.perform(post("/v1/product/")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(productReqDto)))
						.andExpect(status().isOk()).andReturn();

		ProductResDto responseDto = objectMapper
				.readValue(mvcResult.getResponse()
						.getContentAsString(), ProductResDto.class);

		assertEquals(1, responseDto.getId());
	}

	@WithMockUser(roles = {"ADMIN"})
	@Test
	@Sql(scripts = {
			"/data/sql/cleanup.sql",
			"/data/sql/initial_setup.sql"
	})
	void create_NewProduct_withInvalidData_throwException() throws Exception {
		ProductReqDto productReqDto = new ProductReqDto();
		productReqDto.setCode(null);
		productReqDto.setEnabled(true);
		productReqDto.setName(null);
		productReqDto.setPrice(0.01);
		mockMvc.perform(post("/v1/product/")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(productReqDto)))
				.andExpect(status().isBadRequest()).andReturn();

	}

	@WithMockUser(roles = {"ADMIN"})
	@Test
	@Sql(scripts = {
			"/data/sql/cleanup.sql",
			"/data/sql/initial_setup.sql",
			"/data/sql/initial_data.sql"
	})
	void update_NewProduct() throws Exception {

		MvcResult mvcResultGet =
				mockMvc.perform(
						get("/v1/product/{id}",
								1))
						.andExpect(status().isOk()).andReturn();
		ProductResDto productResDto =
				objectMapper.readValue(mvcResultGet.getResponse().getContentAsString(),
						new TypeReference<>() {
						});
		assertEquals(1, productResDto.getId());
		assertEquals("BLACK_COFFEE", productResDto.getCode());

		ProductReqDto productReqDto = new ProductReqDto();
		productReqDto.setId(1L);
		productReqDto.setCode("TEST");
		productReqDto.setEnabled(true);
		productReqDto.setName("Test");
		productReqDto.setPrice(0.01);
		MvcResult mvcResult =
				mockMvc.perform(patch("/v1/product/")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(productReqDto)))
						.andExpect(status().isOk()).andReturn();

		ProductResDto responseDto = objectMapper
				.readValue(mvcResult.getResponse()
						.getContentAsString(), ProductResDto.class);
		assertEquals(1, responseDto.getId());
		assertEquals("TEST", responseDto.getCode());

	}


	@WithMockUser(roles = {"ADMIN"})
	@Test
	@Sql(scripts = {
			"/data/sql/cleanup.sql",
			"/data/sql/initial_setup.sql",
			"/data/sql/initial_data.sql"
	})
	void enable_Product() throws Exception {

		MvcResult mvcResultGet =
				mockMvc.perform(
						get("/v1/product/{id}",
								3))
						.andExpect(status().isOk()).andReturn();
		ProductResDto productResDto =
				objectMapper.readValue(mvcResultGet.getResponse().getContentAsString(),
						new TypeReference<>() {
						});
		assertEquals(3, productResDto.getId());
		assertEquals("MOCHA", productResDto.getCode());
		assertEquals(false, productResDto.getEnabled());

		MvcResult mvcResult =
				mockMvc.perform(patch("/v1/product/enable/{id}", 3))
						.andExpect(status().isOk()).andReturn();

		ProductResDto responseDto = objectMapper
				.readValue(mvcResult.getResponse()
						.getContentAsString(), ProductResDto.class);
		assertEquals(3, responseDto.getId());
		assertEquals("MOCHA", responseDto.getCode());
		assertEquals(true, responseDto.getEnabled());
	}

	@WithMockUser(roles = {"ADMIN"})
	@Test
	@Sql(scripts = {
			"/data/sql/cleanup.sql",
			"/data/sql/initial_setup.sql",
			"/data/sql/initial_data.sql"
	})
	void disable_Product() throws Exception {

		MvcResult mvcResultGet =
				mockMvc.perform(
						get("/v1/product/{id}",
								2))
						.andExpect(status().isOk()).andReturn();
		ProductResDto productResDto =
				objectMapper.readValue(mvcResultGet.getResponse().getContentAsString(),
						new TypeReference<>() {
						});
		assertEquals(2, productResDto.getId());
		assertEquals("LATTE", productResDto.getCode());
		assertEquals(true, productResDto.getEnabled());

		MvcResult mvcResult =
				mockMvc.perform(patch("/v1/product/disable/{id}", 2))
						.andExpect(status().isOk()).andReturn();

		ProductResDto responseDto = objectMapper
				.readValue(mvcResult.getResponse()
						.getContentAsString(), ProductResDto.class);
		assertEquals(2, responseDto.getId());
		assertEquals("LATTE", responseDto.getCode());
		assertEquals(false, responseDto.getEnabled());
	}


}
