package com.starbux.customer.attribute.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.starbux.TestTag;
import com.starbux.configuration.ApiIntegrationTest;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Tag(TestTag.INTEGRATION_TEST)
@ApiIntegrationTest
@ActiveProfiles("INTEGRATIONTEST")
@Sql(scripts = {
		"/data/sql/cleanup.sql",
		"/data/sql/setup_move_stage.sql"
})
class CustomerAttributeControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	void getNonCashAttributeGroup_byPolicyAttributeGroupId() throws Exception {
		MvcResult mvcResult =
				mockMvc.perform(
						get("/v1/customer/policy/attribute/non-cash/policy-benefit/{id}/groups",
								"3cd8b33f-5e9b-41fe-a6ea-9c49a36b6619"))
						.andExpect(status().isOk()).andReturn();

		assertEquals(1, 0);
	}


}
