package com.starbux.web.facade;

import com.starbux.dto.request.UserReqDto;
import com.starbux.enums.Command;

import java.util.Optional;

public interface IOrderFacade<T, U> {

	Optional<U> getOrder(Long id);

	Optional<Boolean> deleteOrder(Long id);

	Optional<U> productOrder(Long cartId, Command command, Long id);

	Optional<U> toppingOrder(Long cartId, Long productId, Command command, Long id);

	Optional<U> confirmOrder(Long id);

	Optional<U> initiateOrder(UserReqDto t);
}
