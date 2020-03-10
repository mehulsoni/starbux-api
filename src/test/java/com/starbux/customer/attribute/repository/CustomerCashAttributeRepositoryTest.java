package com.starbux.customer.attribute.repository;

import com.starbux.TestTag;
import com.starbux.annotation.ApiDataJpaTest;

import org.junit.jupiter.api.Tag;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

//@Tag(TestTag.UNIT_TEST)
//@ApiDataJpaTest
//@Sql(scripts = {
//		"/data/sql/cleanup.sql",
//		"/data/sql/initial_setup.sql"
//})
//@ActiveProfiles("INTEGRATIONTEST")
public class CustomerCashAttributeRepositoryTest {
//  @Autowired
//  private CustomerCashAttributeRepository customerCashAttributeRepository;
//
//  @Autowired
//  private CustomerCashBenefitRepository customerCashBenefitRepository;


//  @Test
//  public void saveAllCashPolicyAttributeGroupsAndOptions() {
//    // find all CustomerNonCashPolicyBenefits by policy template version UUID
//    List<CustomerCashPolicyBenefit> customerNonCashPolicyBenefits = getCustomerNonCashPolicyBenefits();
//
//    assertThat(customerNonCashPolicyBenefits).isNotEmpty();
//    assertThat(customerNonCashPolicyBenefits.get(0).getId()).isNotNull();
//
//    List<CustomerCashPolicyBenefitAttributeGroup> customerCashPolicyBenefitAttributeGroups =
//        getCustomerCashPolicyBenefitAttributeGroupByCustomerCashPolicyBenefit(customerNonCashPolicyBenefits.get(0));
//
//    assertThat(customerCashPolicyBenefitAttributeGroups).isNotEmpty();
//    assertThat(customerCashPolicyBenefitAttributeGroups.get(0).getAttributeOptions()).isNotEmpty();
//
//    List<CustomerCashPolicyBenefitAttributeGroup> saved =
//        customerCashAttributeRepository.saveAll(customerCashPolicyBenefitAttributeGroups);
//
//    assertThat(saved).isNotEmpty();
//    assertThat(saved.get(0).getAttributeOptions()).isNotEmpty();
//  }


}
