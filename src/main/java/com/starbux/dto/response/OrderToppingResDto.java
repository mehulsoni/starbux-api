package com.starbux.dto.response;

public class OrderToppingResDto {

	private Long id;
	private ToppingResDto topping;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ToppingResDto getTopping() {
		return topping;
	}

	public void setTopping(ToppingResDto topping) {
		this.topping = topping;
	}
}
