package com.starbux.constant;

public final class Constant {

	public static final String GET_ORDER_NOT_FOUND_ERROR_MESSAGE = "No Order cart found for given id";
	public static final String PRODUCT_ORDER_BAD_REQUEST_ERROR_MESSAGE = "There is some issue with payload, please check server logs";
	public static final String TOPPING_ORDER_BAD_REQUEST_ERROR_MESSAGE = "There is some issue with payload, please check server logs";
	public static final String CONFIRM_ORDER_BAD_REQUEST_ERROR_MESSAGE = "There is some issue in confirming order, please check server logs";
	public static final String INITIATE_ORDER_BAD_REQUEST_ERROR_MESSAGE = "There is some issue in initiating order, please check server logs";
	public static final String CANCEL_ORDER_NOT_FOUND_ERROR_MESSAGE = "No cart order found for given id";

	private Constant() {
		super();
	}

}
