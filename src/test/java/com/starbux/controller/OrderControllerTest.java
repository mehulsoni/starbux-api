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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Tag(TestTag.INTEGRATION_TEST)
@ApiIntegrationTest
@ActiveProfiles("INTEGRATIONTEST")
public class OrderControllerTest {

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


	@Test
	@Sql(scripts = {
			"/data/sql/cleanup.sql",
			"/data/sql/initial_setup.sql",
			"/data/sql/initial_data.sql"
	})
	void testInitiateOrder_empty_data() throws Exception {

		UserReqDto userReqDto = new UserReqDto();

		MvcResult mvcResult =
				mockMvc.perform(post("/v1/order/initiate-order")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(userReqDto)))
						.andExpect(status().isOk()).andReturn();
		OrderResponseDto orderResponseDto =
				objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
						new TypeReference<>() {
						});
		assertEquals(1, orderResponseDto.getId());
		assertEquals("user", orderResponseDto.getUser().getFirstName());
	}

	@Test
	@Sql(scripts = {
			"/data/sql/cleanup.sql",
			"/data/sql/initial_setup.sql",
			"/data/sql/initial_data.sql"
	})
	void getOrder_ById() throws Exception {
		testInitiateOrder_success();
		MvcResult mvcResult =
				mockMvc.perform(
						get("/v1/order/{id}",
								1))
						.andExpect(status().isOk()).andReturn();
		OrderResponseDto orderResponseDtoGet =
				objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
						new TypeReference<>() {
						});
		assertEquals(1, orderResponseDtoGet.getId());
		assertEquals("Test", orderResponseDtoGet.getUser().getFirstName());
	}

	@Test
	@Sql(scripts = {
			"/data/sql/cleanup.sql",
			"/data/sql/initial_setup.sql",
			"/data/sql/initial_data.sql"
	})
	void getOrder_ByInvalidId() throws Exception {
		MvcResult mvcResult =
				mockMvc.perform(
						get("/v1/order/{id}",
								1))
						.andExpect(status().isNotFound()).andReturn();

	}

	@Test
	@Sql(scripts = {
			"/data/sql/cleanup.sql",
			"/data/sql/initial_setup.sql",
			"/data/sql/initial_data.sql"
	})
	void put_add_product_order() throws Exception {
		testInitiateOrder_success();
		MvcResult mvcResult =
				mockMvc.perform(
						put("/v1/order/product-order/cart-id/{cart-id}/command/{command}/id/{id}",
								1, "ADD", 2))
						.andExpect(status().isOk()).andReturn();
		OrderResponseDto orderResponseDtoGet =
				objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
						new TypeReference<>() {
						});
		assertEquals(1, orderResponseDtoGet.getId());
	}

	@Test
	@Sql(scripts = {
			"/data/sql/cleanup.sql",
			"/data/sql/initial_setup.sql",
			"/data/sql/initial_data.sql"
	})
	void put_remove_product_order() throws Exception {
		testInitiateOrder_success();
		MvcResult mvcResultPut =
				mockMvc.perform(
						put("/v1/order/product-order/cart-id/{cart-id}/command/{command}/id/{id}",
								1, "ADD", 2))
						.andExpect(status().isOk()).andReturn();
		OrderResponseDto orderResponseDtoPut =
				objectMapper.readValue(mvcResultPut.getResponse().getContentAsString(),
						new TypeReference<>() {
						});
		assertEquals(1, orderResponseDtoPut.getId());

		MvcResult mvcResult =
				mockMvc.perform(
						put("/v1/order/product-order/cart-id/{cart-id}/command/{command}/id/{id}",
								1, "REMOVE", 1))
						.andExpect(status().isOk()).andReturn();
		OrderResponseDto orderResponseDtoGet =
				objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
						new TypeReference<>() {
						});
		assertEquals(0, orderResponseDtoGet.getOrderProducts().size());
	}

	@Test
	@Sql(scripts = {
			"/data/sql/cleanup.sql",
			"/data/sql/initial_setup.sql",
			"/data/sql/initial_data.sql"
	})
	void put_remove_product_order_InvalidCommand_ThrowException() throws Exception {
		testInitiateOrder_success();
		MvcResult mvcResultPut =
				mockMvc.perform(
						put("/v1/order/product-order/cart-id/{cart-id}/command/{command}/id/{id}",
								1, "ADD", 2))
						.andExpect(status().isOk()).andReturn();
		OrderResponseDto orderResponseDtoPut =
				objectMapper.readValue(mvcResultPut.getResponse().getContentAsString(),
						new TypeReference<>() {
						});
		assertEquals(1, orderResponseDtoPut.getId());

		MvcResult mvcResult =
				mockMvc.perform(
						put("/v1/order/product-order/cart-id/{cart-id}/command/{command}/id/{id}",
								1, "WRONG", 1))
						.andExpect(status().isBadRequest()).andReturn();
	}

	@Test
	@Sql(scripts = {
			"/data/sql/cleanup.sql",
			"/data/sql/initial_setup.sql",
			"/data/sql/initial_data.sql"
	})
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

	@Test
	@Sql(scripts = {
			"/data/sql/cleanup.sql",
			"/data/sql/initial_setup.sql",
			"/data/sql/initial_data.sql"
	})
	void put_remove_product_topping_order() throws Exception {
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

		MvcResult mvcResultToppingRemove =
				mockMvc.perform(
						put("/v1/order/topping-order/cart-id/{cart-id}/order-product-id/{order-product-id}/command/{command}/id/{id}",
								1, orderResponseDtoProduct.getOrderProducts().get(0).getId(), "REMOVE",
								orderResponseDtoTopping.getOrderProducts().get(0).getOrderToppings().get(0).getId()))
						.andExpect(status().isOk()).andReturn();
		OrderResponseDto orderResponseDtoToppingRemove =
				objectMapper.readValue(mvcResultToppingRemove.getResponse().getContentAsString(),
						new TypeReference<>() {
						});
		assertEquals(1, orderResponseDtoToppingRemove.getId());
		assertEquals(1, orderResponseDtoToppingRemove.getOrderProducts().get(0).getId());
		assertEquals(0, orderResponseDtoToppingRemove.getOrderProducts().get(0).getOrderToppings().size());
	}

	@Test
	@Sql(scripts = {
			"/data/sql/cleanup.sql",
			"/data/sql/initial_setup.sql",
			"/data/sql/initial_data.sql"
	})
	void  cancelOrderTest() throws Exception {
		testInitiateOrder_success();
		MvcResult mvcResultDelete =
				mockMvc.perform(
						delete("/v1/order/cancel-order/{id}",
								1))
						.andExpect(status().isOk()).andReturn();

		assertEquals(200, mvcResultDelete.getResponse().getStatus());
	}


	@Test
	@Sql(scripts = {
			"/data/sql/cleanup.sql",
			"/data/sql/initial_setup.sql",
			"/data/sql/initial_data.sql"
	})
	void  cancelOrderTest_InvalidId_throwException() throws Exception {
		MvcResult mvcResultDelete =
				mockMvc.perform(
						delete("/v1/order/cancel-order/{id}",
								1))
						.andExpect(status().isNotFound()).andReturn();

		assertEquals(404, mvcResultDelete.getResponse().getStatus());
	}

	@Test
	@Sql(scripts = {
			"/data/sql/cleanup.sql",
			"/data/sql/initial_setup.sql",
			"/data/sql/initial_data.sql"
	})
	void confirmOrderTest() throws Exception {
		put_add_product_topping_order();
		MvcResult mvcResult =
				mockMvc.perform(
						get("/v1/order/confirm-order/{id}",
								1))
						.andExpect(status().isOk()).andReturn();

		OrderResponseDto orderResponseDtoToppingConfirm =
				objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
						new TypeReference<>() {
						});
		assertEquals(200, mvcResult.getResponse().getStatus());
		assertEquals(8.00, orderResponseDtoToppingConfirm.getAmount().doubleValue());
		assertEquals(0, orderResponseDtoToppingConfirm.getDiscount().doubleValue());
	}
}
