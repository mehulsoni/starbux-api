package com.starbux.web.facade.impl;

import com.starbux.dto.request.OrderRequestDto;
import com.starbux.dto.request.UserReqDto;
import com.starbux.dto.response.OrderResponseDto;
import com.starbux.enums.Command;
import com.starbux.web.facade.IOrderFacade;
import com.starbux.web.service.IOrderService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OrderFacadeImpl implements IOrderFacade {

	@Autowired
	private IOrderService<OrderRequestDto, OrderResponseDto> orderService;

	@Override
	public Optional getOrder(Long id) {
		return orderService.getOrder(id);
	}

	@Override
	public Optional<Boolean> deleteOrder(Long id) {
		return orderService.deleteOrder(id);
	}

	@Override
	public Optional productOrder(Long cartId, Command command, Long id) {
		orderService.productOrder(cartId, command, id);
		return orderService.updateCart(cartId);
	}

	@Override
	public Optional toppingOrder(Long cartId, Long productId, Command command, Long id) {
		orderService.toppingOrder(cartId, productId, command, id);
		return orderService.updateCart(cartId);
	}

	@Override
	public Optional confirmOrder(Long id) {
		return orderService.confirmOrder(id);
	}

	@Override
	public Optional initiateOrder(UserReqDto reqDto) {
		return orderService.initiateOrder(reqDto);
	}
}
