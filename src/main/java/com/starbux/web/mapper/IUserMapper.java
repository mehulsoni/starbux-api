package com.starbux.web.mapper;

import com.starbux.dto.request.UserReqDto;
import com.starbux.entity.OrderPerCustomerReportDto;
import com.starbux.entity.User;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper
public interface IUserMapper {
	@Mapping(target = "id", ignore = true)
	@Mapping(target = "orders", ignore = true)
	User dtoToEntity(UserReqDto userReqDto);

	@Mapping(target = "amount", ignore = true)
	OrderPerCustomerReportDto entityToOrderPerCustomerReportDto(User user);

	List<OrderPerCustomerReportDto> entityToOrderPerCustomerReportDtoList(List<User> users);
}
