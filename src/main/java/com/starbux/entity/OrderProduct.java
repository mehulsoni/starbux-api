package com.starbux.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "order_product")
public class OrderProduct {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "order_cart_id")
	private OrderCart orderCart;

	@OneToOne
	@JoinColumn(name = "product_id",
			referencedColumnName = "id")
	private Product product;

	@OneToMany(mappedBy = "orderProduct",  cascade = CascadeType.ALL,
			fetch = FetchType.LAZY)
	private List<OrderTopping> orderToppings;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public OrderCart getOrderCart() {
		return orderCart;
	}

	public void setOrderCart(OrderCart orderCart) {
		this.orderCart = orderCart;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public List<OrderTopping> getOrderToppings() {
		return orderToppings;
	}

	public void setOrderToppings(List<OrderTopping> orderToppings) {
		this.orderToppings = orderToppings;
	}
}
