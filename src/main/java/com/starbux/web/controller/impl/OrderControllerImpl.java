package com.starbux.web.controller.impl;

import com.starbux.constant.Constant;
import com.starbux.dto.request.OrderRequestDto;
import com.starbux.dto.request.UserReqDto;
import com.starbux.dto.response.OrderResponseDto;
import com.starbux.enums.Command;
import com.starbux.web.controller.IOrderController;
import com.starbux.web.facade.IOrderFacade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/v1/order")
@Tag(description = "Order CRUD Operation", name = "Order C/R/U/D")
public class OrderControllerImpl implements IOrderController<OrderRequestDto, OrderResponseDto> {


	@Autowired
	private IOrderFacade<OrderRequestDto, OrderResponseDto> facade;

	@Override
	@GetMapping("/{id}")
	public ResponseEntity<OrderResponseDto> getOrder(@PathVariable("id") Long id) {
		return facade.getOrder(id)
				.map(ResponseEntity::ok)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, Constant.GET_ORDER_NOT_FOUND_ERROR_MESSAGE));
	}

	@Override
	@DeleteMapping("/cancel-order/{id}")
	public void cancelOrder(@PathVariable("id") Long id) {
		facade.deleteOrder(id).map(ResponseEntity::ok)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, Constant.CANCEL_ORDER_NOT_FOUND_ERROR_MESSAGE));
	}

	@PutMapping("/product-order/cart-id/{cart-id}/command/{command}/id/{id}")
	@Override
	public ResponseEntity<OrderResponseDto> productOrder(@PathVariable("cart-id") Long cartId,
	                                                     @PathVariable("command") Command command,
	                                                     @PathVariable("id") Long id) {
		return facade.productOrder(cartId, command, id).map(ResponseEntity::ok)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, Constant.PRODUCT_ORDER_BAD_REQUEST_ERROR_MESSAGE));
	}

	@PutMapping("/topping-order/cart-id/{cart-id}/order-product-id/{order-product-id}/command/{command}/id/{id}")
	@Override
	public ResponseEntity<OrderResponseDto> toppingOrder(@PathVariable("cart-id") Long cartId,
	                                                     @PathVariable("order-product-id") Long orderProductId,
	                                                     @PathVariable("command") Command command,
	                                                     @PathVariable("id") Long id) {

		return facade.toppingOrder(cartId, orderProductId, command, id).map(ResponseEntity::ok)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, Constant.TOPPING_ORDER_BAD_REQUEST_ERROR_MESSAGE));
	}

	@Override
	@GetMapping("/confirm-order/{id}")
	public ResponseEntity<OrderResponseDto> confirmOrder(@PathVariable("id") Long id) {
		return facade.confirmOrder(id)
				.map(ResponseEntity::ok)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, Constant.CONFIRM_ORDER_BAD_REQUEST_ERROR_MESSAGE));
	}

	@Override
	@PostMapping("/initiate-order")
	public ResponseEntity<OrderResponseDto> initiateOrder(@RequestBody UserReqDto request) {
		return facade.initiateOrder(request)
				.map(ResponseEntity::ok)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, Constant.INITIATE_ORDER_BAD_REQUEST_ERROR_MESSAGE));

	}
}
