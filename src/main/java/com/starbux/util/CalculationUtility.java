package com.starbux.util;

import com.starbux.entity.OrderProduct;
import com.starbux.entity.OrderTopping;

import org.apache.commons.collections4.CollectionUtils;

import java.util.List;

public class CalculationUtility {

	private CalculationUtility() {
		super();
	}

	public static Double sum(List<OrderProduct> orderProduct) {
		if (CollectionUtils.isNotEmpty(orderProduct)) {
			return orderProduct.stream()
					.map(x -> calculateToppingPrice(x.getOrderToppings()) + calculateDrinkPrice(x))
					.reduce(0.0, Double::sum);
		} else {
			return 0.0;
		}
	}

	private static Double calculateToppingPrice(List<OrderTopping> oToppings) {
		if (CollectionUtils.isNotEmpty(oToppings)) {
			return
					oToppings.stream()
							.map(c -> c.getTopping().getPrice())
							.reduce(0.0, Double::sum);
		} else {
			return 0.0;
		}
	}

	private static double calculateDrinkPrice(OrderProduct x) {
		if (null != x && null != x.getProduct()) {
			return x.getProduct().getPrice();
		} else {
			return 0.0;
		}
	}

	public static double discount(List<OrderProduct> orderProducts) {
		if (CollectionUtils.isNotEmpty(orderProducts)) {
			Double discount = applyPercentDiscount(orderProducts, 12.0, 25);
			return applyNumberOfDrinkDiscount(discount, orderProducts, 3);
		} else {
			return 0.0;
		}
	}

	private static double applyPercentDiscount(List<OrderProduct> orderProducts,
	                                           double offerPrice,
	                                           double percentage) {
		double sum = sum(orderProducts);
		if (sum > offerPrice) {
			return calculatePercentage(percentage, sum);
		}
		return 0.0;
	}

	private static double calculatePercentage(double percentage, double sum) {
		return ((percentage * sum) / 100);
	}

	private static double applyNumberOfDrinkDiscount(Double discount, List<OrderProduct> orderProducts, int numberOfDrinksAllowed) {
		if (CollectionUtils.isNotEmpty(orderProducts) && orderProducts.size() >= numberOfDrinksAllowed) {
			return Math.min(discount, min(orderProducts));
		}
		return discount;
	}

	private static Double min(List<OrderProduct> orderProducts) {
		if (CollectionUtils.isNotEmpty(orderProducts)) {
			return orderProducts
					.stream()
					.map(x -> (calculateToppingPrice(x.getOrderToppings()) + calculateDrinkPrice(x)))
					.reduce(Double.MAX_VALUE, Double::min);
		} else {
			return Double.MIN_VALUE;
		}
	}
}
