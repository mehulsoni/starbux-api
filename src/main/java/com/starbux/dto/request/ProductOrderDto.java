package com.starbux.dto.request;

import com.starbux.enums.Command;

import java.util.List;

public class ProductOrderDto {

	private Long id;
	private Command command;
	private List<ToppingOrderDto> toppingOrders;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<ToppingOrderDto> getToppingOrders() {
		return toppingOrders;
	}

	public void setToppingOrders(List<ToppingOrderDto> toppingOrders) {
		this.toppingOrders = toppingOrders;
	}

	public Command getCommand() {
		return command;
	}

	public void setCommand(Command command) {
		this.command = command;
	}
}
