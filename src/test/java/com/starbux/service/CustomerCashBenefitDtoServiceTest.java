package com.starbux.service;

import com.starbux.TestTag;
import com.starbux.configuration.ApiIntegrationTest;

import org.junit.jupiter.api.Tag;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

//@Tag(TestTag.UNIT_TEST)
//@ApiIntegrationTest
//@ActiveProfiles("INTEGRATIONTEST")
//@Sql(scripts = {
//		"/data/sql/cleanup.sql",
//		"/data/sql/initial_setup.sql"
//})
class CustomerCashBenefitDtoServiceTest {

//  @Autowired
//  private CustomerCashAttributeRepository customerCashAttributeRepository;
//
//  @Autowired
//  private CustomerCashBenefitRepository customerCashBenefitRepository;
//
//  @Autowired
//  private CustomerCashBenefitService customerCashBenefitService;
//
//  @Test
//  void removeAllCustomerAttributeGroupsWhenCashPolicyBenefitRemoved() {
//    UUID cashPolicyBenefitId = UUID.fromString("3da3f078-5e1a-43fb-81c1-cb0ed5f74427");
//    Optional<CustomerCashPolicyBenefit> customerCashPolicyBenefitOptional =
//        customerCashBenefitRepository.findById(cashPolicyBenefitId);
//
//    assertThat(customerCashPolicyBenefitOptional).isPresent();
//
//    CustomerCashPolicyBenefitRemoveDto requestDto = new CustomerCashPolicyBenefitRemoveDto();
//    requestDto.setCustomerCashBenefitIds(Lists.newArrayList(cashPolicyBenefitId));
//
//    customerCashBenefitService.removeCashPolicyBenefit(requestDto);
//
//    //Make sure that all attribute groups and options associated with this non-cash benefit are removed
//    CustomerCashPolicyBenefit customerCashPolicyBenefit = customerCashPolicyBenefitOptional.get();
//    List<CustomerCashPolicyBenefitAttributeGroup> policyScopeGroups =
//        customerCashAttributeRepository.findByPolicyBenefit(customerCashPolicyBenefit);
//    assertThat(policyScopeGroups).isEmpty();
//  }
}
