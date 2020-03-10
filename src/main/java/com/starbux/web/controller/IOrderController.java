package com.starbux.web.controller;

import com.starbux.dto.request.UserReqDto;
import com.starbux.enums.Command;

import org.springframework.http.ResponseEntity;

public interface IOrderController<T, U> {

	ResponseEntity<U> getOrder(Long id);

	void cancelOrder(Long id);

	ResponseEntity<U> productOrder(Long cartId, Command command, Long id);

	ResponseEntity<U> toppingOrder(Long cartId, Long productId, Command command, Long id);

	ResponseEntity<U> confirmOrder(Long id);

	ResponseEntity<U> initiateOrder(UserReqDto user);

}
