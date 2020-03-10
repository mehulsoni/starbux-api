package com.starbux.dto.request;

import java.util.List;

public class OrderRequestDto {

	private Long id; //order cart id
	private List<ProductOrderDto> productOrders;
	private UserReqDto userReqDto;

	public List<ProductOrderDto> getProductOrders() {
		return productOrders;
	}

	public void setProductOrders(List<ProductOrderDto> productOrders) {
		this.productOrders = productOrders;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public UserReqDto getUserReqDto() {
		return userReqDto;
	}

	public void setUserReqDto(UserReqDto userReqDto) {
		this.userReqDto = userReqDto;
	}
}
