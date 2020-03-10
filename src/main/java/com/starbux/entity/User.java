package com.starbux.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "star_bux_customer")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "first_name")
	private String firstName = "user";

	@Column(name = "last_name")
	private String lastName = "anonymous";
	
	@Column(name = "email")
	private String email = "test@starbux.com";

	@Column(name = "mobile")
	private Long mobile = 0000000000L;

	@OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
	private List<OrderCart> orders;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Long getMobile() {
		return mobile;
	}

	public void setMobile(Long mobile) {
		this.mobile = mobile;
	}

	public List<OrderCart> getOrders() {
		return orders;
	}

	public void setOrders(List<OrderCart> orders) {
		this.orders = orders;
	}
}
