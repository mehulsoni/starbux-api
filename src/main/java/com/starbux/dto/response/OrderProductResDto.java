package com.starbux.dto.response;

import java.util.List;

public class OrderProductResDto {

	private Long id;
	private ProductResDto product;
	private List<OrderToppingResDto> orderToppings;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ProductResDto getProduct() {
		return product;
	}

	public void setProduct(ProductResDto product) {
		this.product = product;
	}

	public List<OrderToppingResDto> getOrderToppings() {
		return orderToppings;
	}

	public void setOrderToppings(List<OrderToppingResDto> orderToppings) {
		this.orderToppings = orderToppings;
	}
}
