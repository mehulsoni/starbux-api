package com.starbux.web.service;

import com.starbux.dto.request.UserReqDto;
import com.starbux.enums.Command;

import java.util.Optional;
import java.util.stream.DoubleStream;

public interface IOrderService<T, U> {

	Optional<U> getOrder(Long id);

	Optional<Boolean> deleteOrder(Long id);

	void productOrder(Long cartId, Command command, Long id);

	void toppingOrder(Long cartId, Long productId, Command command, Long id);

	Optional<U> confirmOrder(Long id);

	Optional<U> initiateOrder(UserReqDto t);

	Optional<U> updateCart(Long cartId);
}
