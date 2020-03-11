package com.starbux.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.starbux.TestTag;
import com.starbux.configuration.ApiIntegrationTest;
import com.starbux.dto.request.UserReqDto;
import com.starbux.dto.response.OrderResponseDto;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Tag(TestTag.INTEGRATION_TEST)
@ApiIntegrationTest
@ActiveProfiles("INTEGRATIONTEST")
public class ReportGeneratorTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	void testInitiateOrder_success() throws Exception {
		MvcResult mvcResult = createOrder();
		OrderResponseDto orderResponseDto =
				objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
						new TypeReference<>() {
						});
		assertEquals(1, orderResponseDto.getId());
		assertEquals("Test", orderResponseDto.getUser().getFirstName());
	}


	private MvcResult createOrder() throws Exception {
		UserReqDto userReqDto = new UserReqDto();
		userReqDto.setEmail("test@gmail.com");
		userReqDto.setFirstName("Test");
		userReqDto.setLastName("Test");
		userReqDto.setMobile(900000000L);

		return mockMvc.perform(post("/v1/order/initiate-order")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(userReqDto)))
				.andExpect(status().isOk()).andReturn();
	}

	void put_add_product_topping_order() throws Exception {
		testInitiateOrder_success();
		MvcResult mvcResultProduct =
				mockMvc.perform(
						put("/v1/order/product-order/cart-id/{cart-id}/command/{command}/id/{id}",
								1, "ADD", 2))
						.andExpect(status().isOk()).andReturn();
		OrderResponseDto orderResponseDtoProduct =
				objectMapper.readValue(mvcResultProduct.getResponse().getContentAsString(),
						new TypeReference<>() {
						});
		assertEquals(1, orderResponseDtoProduct.getId());

		MvcResult mvcResultTopping =
				mockMvc.perform(
						put("/v1/order/topping-order/cart-id/{cart-id}/order-product-id/{order-product-id}/command/{command}/id/{id}",
								1, orderResponseDtoProduct.getOrderProducts().get(0).getId(), "ADD", 2))
						.andExpect(status().isOk()).andReturn();
		OrderResponseDto orderResponseDtoTopping =
				objectMapper.readValue(mvcResultTopping.getResponse().getContentAsString(),
						new TypeReference<>() {
						});
		assertEquals(1, orderResponseDtoTopping.getId());
		assertEquals(1, orderResponseDtoTopping.getOrderProducts().get(0).getId());
	}

	@WithMockUser(roles = {"ADMIN"})
	@Test
	@Sql(scripts = {
			"/data/sql/cleanup.sql",
			"/data/sql/initial_setup.sql",
			"/data/sql/initial_data.sql"
	})
	void testGenerateReport() throws Exception {
		put_add_product_topping_order();
		MvcResult mvcResult =
				mockMvc.perform(
						get("/v1/report/order-per-customer"))
						.andExpect(status().isOk()).andReturn();
		assertEquals(200, mvcResult.getResponse().getStatus());
	}

}
