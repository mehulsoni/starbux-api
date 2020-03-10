package com.starbux.web.mapper;

import com.starbux.dto.response.OrderResponseDto;
import com.starbux.entity.OrderCart;

import org.mapstruct.Mapper;

@Mapper
public interface IOrderMapper {
	OrderResponseDto entityToResponseDto(OrderCart savedOrder);
}
