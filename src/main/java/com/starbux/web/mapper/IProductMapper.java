package com.starbux.web.mapper;

import com.starbux.dto.request.ProductReqDto;
import com.starbux.dto.response.ProductResDto;
import com.starbux.entity.Product;

import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface IProductMapper {
	Product requestDtoToEntity(ProductReqDto productReqDto);

	ProductResDto entityToResponseDto(Product product);

	List<ProductResDto> entitiesToResponseDtoList(List<Product> products);
}
