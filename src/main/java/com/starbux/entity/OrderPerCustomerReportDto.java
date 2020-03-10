package com.starbux.entity;

import java.util.StringJoiner;

public class OrderPerCustomerReportDto {

	private Integer id;
	private String name;
	private String email;
	private Double amount;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String firstName, String lastName) {
		StringJoiner stringJoiner = new StringJoiner(" ");
		stringJoiner.add(firstName);
		stringJoiner.add(lastName);
		this.name = stringJoiner.toString();
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}
}
