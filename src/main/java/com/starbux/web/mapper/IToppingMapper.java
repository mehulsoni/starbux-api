package com.starbux.web.mapper;

import com.starbux.dto.request.ToppingReqDto;
import com.starbux.dto.response.ToppingResDto;
import com.starbux.entity.Topping;

import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface IToppingMapper {
	Topping requestDtoToEntity(ToppingReqDto toppingReqDto);

	ToppingResDto entityToResponseDto(Topping savedTopping);

	List<ToppingResDto> entitiesToResponseDtoList(List<Topping> toppings);
}
