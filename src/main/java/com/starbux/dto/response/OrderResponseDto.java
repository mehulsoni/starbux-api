package com.starbux.dto.response;

import com.starbux.dto.request.UserReqDto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class OrderResponseDto {

	private Long id;
	private BigDecimal amount;
	private BigDecimal discount;
	private Date date;
	private UserReqDto user;

	private List<OrderProductResDto> orderProducts;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getDiscount() {
		return discount;
	}

	public void setDiscount(BigDecimal discount) {
		this.discount = discount;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public UserReqDto getUser() {
		return user;
	}

	public void setUser(UserReqDto user) {
		this.user = user;
	}

	public List<OrderProductResDto> getOrderProducts() {
		return orderProducts;
	}

	public void setOrderProducts(List<OrderProductResDto> orderProducts) {
		this.orderProducts = orderProducts;
	}
}
