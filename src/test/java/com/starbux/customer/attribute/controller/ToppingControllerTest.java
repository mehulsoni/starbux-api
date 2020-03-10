package com.starbux.customer.attribute.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.starbux.TestTag;
import com.starbux.configuration.ApiIntegrationTest;
import com.starbux.dto.request.ToppingReqDto;
import com.starbux.dto.response.ProductResDto;
import com.starbux.dto.response.ToppingResDto;

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
class ToppingControllerTest {

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
	void getTopping_byId() throws Exception {
		MvcResult mvcResult =
				mockMvc.perform(
						get("/v1/topping/{id}",
								1))
						.andExpect(status().isOk()).andReturn();
		ToppingResDto toppingResDto =
				objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
						new TypeReference<>() {
						});
		assertEquals(1, toppingResDto.getId());
	}

	@Test
	@Sql(scripts = {
			"/data/sql/cleanup.sql",
			"/data/sql/initial_setup.sql",
			"/data/sql/initial_data.sql"
	})
	void get_All_Topping() throws Exception {
		MvcResult mvcResult =
				mockMvc.perform(
						get("/v1/topping/"))
						.andExpect(status().isOk()).andReturn();
		List<ProductResDto> toppingResDto =
				objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
						new TypeReference<>() {
						});

		assertEquals(4, toppingResDto.size());
	}

	@WithMockUser(roles = {"ADMIN"})
	@Test
	@Sql(scripts = {
			"/data/sql/cleanup.sql",
			"/data/sql/initial_setup.sql"
	})
	void create_NewTopping() throws Exception {
		ToppingReqDto toppingReqDto = new ToppingReqDto();
		toppingReqDto.setCode("TEST");
		toppingReqDto.setEnabled(true);
		toppingReqDto.setName("Test");
		toppingReqDto.setPrice(0.01);
		MvcResult mvcResult =
				mockMvc.perform(post("/v1/topping/")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(toppingReqDto)))
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
			"/data/sql/initial_setup.sql",
			"/data/sql/initial_data.sql"
	})
	void update_NewTopping() throws Exception {

		MvcResult mvcResultGet =
				mockMvc.perform(
						get("/v1/topping/{id}",
								1))
						.andExpect(status().isOk()).andReturn();
		ToppingResDto toppingResDto =
				objectMapper.readValue(mvcResultGet.getResponse().getContentAsString(),
						new TypeReference<>() {
						});
		assertEquals(1, toppingResDto.getId());
		assertEquals("MILK", toppingResDto.getCode());

		ToppingReqDto toppingReqDto = new ToppingReqDto();
		toppingReqDto.setId(1L);
		toppingReqDto.setCode("TEST");
		toppingReqDto.setEnabled(true);
		toppingReqDto.setName("Test");
		toppingReqDto.setPrice(0.01);
		MvcResult mvcResult =
				mockMvc.perform(patch("/v1/topping/")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(toppingReqDto)))
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
	void enable_Topping() throws Exception {

		MvcResult mvcResultGet =
				mockMvc.perform(
						get("/v1/topping/{id}",
								3))
						.andExpect(status().isOk()).andReturn();
		ToppingResDto toppingResDto =
				objectMapper.readValue(mvcResultGet.getResponse().getContentAsString(),
						new TypeReference<>() {
						});
		assertEquals(3, toppingResDto.getId());
		assertEquals("CHOCOLATE_SAUCE", toppingResDto.getCode());
		assertEquals(false, toppingResDto.getEnabled());


		MvcResult mvcResult =
				mockMvc.perform(patch("/v1/topping/enable/{id}", 3))
						.andExpect(status().isOk()).andReturn();

		ProductResDto responseDto = objectMapper
				.readValue(mvcResult.getResponse()
						.getContentAsString(), ProductResDto.class);

		assertEquals(3, responseDto.getId());
		assertEquals("CHOCOLATE_SAUCE", responseDto.getCode());
		assertEquals(true, responseDto.getEnabled());
	}

	@WithMockUser(roles = {"ADMIN"})
	@Test
	@Sql(scripts = {
			"/data/sql/cleanup.sql",
			"/data/sql/initial_setup.sql",
			"/data/sql/initial_data.sql"
	})
	void  disable_Topping() throws Exception {

		MvcResult mvcResultGet =
				mockMvc.perform(
						get("/v1/topping/{id}",
								2))
						.andExpect(status().isOk()).andReturn();
		ToppingResDto toppingResDto =
				objectMapper.readValue(mvcResultGet.getResponse().getContentAsString(),
						new TypeReference<>() {
						});
		assertEquals(2, toppingResDto.getId());
		assertEquals("HAZELNUT_SYRUP", toppingResDto.getCode());
		assertEquals(true, toppingResDto.getEnabled());


		MvcResult mvcResult =
				mockMvc.perform(patch("/v1/topping/disable/{id}", 2))
						.andExpect(status().isOk()).andReturn();

		ProductResDto responseDto = objectMapper
				.readValue(mvcResult.getResponse()
						.getContentAsString(), ProductResDto.class);

		assertEquals(2, responseDto.getId());
		assertEquals("HAZELNUT_SYRUP", responseDto.getCode());
		assertEquals(false, responseDto.getEnabled());
	}
}
