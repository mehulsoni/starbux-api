package com.starbux.web.service.impl;

import com.starbux.dto.request.OrderRequestDto;
import com.starbux.dto.request.UserReqDto;
import com.starbux.dto.response.OrderResponseDto;
import com.starbux.entity.OrderCart;
import com.starbux.entity.OrderProduct;
import com.starbux.entity.OrderTopping;
import com.starbux.entity.Product;
import com.starbux.entity.Topping;
import com.starbux.entity.User;
import com.starbux.enums.Command;
import com.starbux.exception.InvalidTypeException;
import com.starbux.exception.NotFoundException;
import com.starbux.util.CalculationUtility;
import com.starbux.web.mapper.IOrderMapper;
import com.starbux.web.mapper.IUserMapper;
import com.starbux.web.repository.IOrderProductRepository;
import com.starbux.web.repository.IOrderRepository;
import com.starbux.web.repository.IOrderToppingRepository;
import com.starbux.web.repository.IProductRepository;
import com.starbux.web.repository.IToppingRepository;
import com.starbux.web.service.IOrderService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class OrderServiceImpl implements IOrderService<OrderRequestDto, OrderResponseDto> {

	@Autowired
	private IOrderRepository orderRepository;

	@Autowired
	private IOrderMapper orderMapper;

	@Autowired
	private IProductRepository productRepository;

	@Autowired
	private IToppingRepository toppingRepository;

	@Autowired
	private IOrderProductRepository orderProductRepository;

	@Autowired
	private IOrderToppingRepository orderToppingRepository;

	@Autowired
	private IUserMapper userMapper;

	/**
	 * This method is used for getting order details based on cart id
	 *
	 * @param id
	 * @return
	 */
	@Override

	public Optional<OrderResponseDto> getOrder(Long id) {
		Optional<OrderCart> orderCart = orderRepository.findById(id);
		if (orderCart.isPresent()) {
			OrderCart savedOrderCart = orderCart.get();
			return Optional.ofNullable(orderMapper.entityToResponseDto(savedOrderCart));
		} else {
			throw new NotFoundException("No order present in cart for given id: " + id);
		}
	}

	/**
	 * This method is user for deleting oder and its products and topping on cancelling order
	 *
	 * @param id
	 * @return
	 */
	@Override

	public Optional<Boolean> deleteOrder(Long id) {
		if (orderRepository.existsById(id)) {
			orderRepository.deleteById(id);
			return Optional.of(true);
		}
		throw new NotFoundException("No Cart Order present for given id");
	}

	/**
	 * This method is used for adding item based on type [PRODUCTS/TOPPINGS]
	 *
	 * @param cartId
	 * @param command
	 * @param id
	 * @return
	 */
	@Override

	public void productOrder(Long cartId, Command command, Long id) {
		switch (command) {
			case ADD:
				addProduct(cartId, id);
				break;
			case REMOVE:
				removeProduct(cartId, id);
				break;
			default:
				throw new InvalidTypeException("Invalid type passed accepted values are [TOPPINGS/PRODUCTS]");
		}
	}

	/**
	 * This method is used for removing item based on type [PRODUCTS/TOPPINGS]
	 *
	 * @param cartId
	 * @param orderProductId
	 * @param command
	 * @param id
	 * @return
	 */
	@Override

	public void toppingOrder(Long cartId, Long orderProductId, Command command, Long id) {
		switch (command) {
			case ADD:
				addTopping(cartId, orderProductId, id);
				break;
			case REMOVE:
				removeTopping(cartId, orderProductId, id);
				break;
			default:
				throw new InvalidTypeException("Invalid type passed accepted values are [TOPPINGS/PRODUCTS]");
		}
	}

	/**
	 * This method is used for adding topping into cart.
	 *
	 * @param cartId
	 * @param orderProductId
	 * @param id
	 * @return
	 */
	private void addTopping(Long cartId, Long orderProductId, Long id) {
		checkAndGetOrderCartIfExist(cartId);
		OrderProduct orderProduct = checkAndGetOrderProductIfExist(orderProductId);
		Topping topping = checkAndGetToppingIfExist(id);
		createTopping(orderProduct, topping);
	}

	/**
	 * This method is used to get OrderCart if exist else throw NotFoundException
	 *
	 * @param cartId
	 * @return
	 */

	private OrderCart checkAndGetOrderCartIfExist(Long cartId) {
		Optional<OrderCart> orderCartOptional = orderRepository.findById(cartId);
		if (orderCartOptional.isPresent()) {
			return orderCartOptional.get();
		} else {
			throw new NotFoundException("No Cart found for given id:" + cartId);
		}
	}

	/**
	 * This method is used to get OrderProduct if exist else throw NotFoundException
	 *
	 * @param orderProductId
	 * @return
	 */

	private OrderProduct checkAndGetOrderProductIfExist(Long orderProductId) {
		Optional<OrderProduct> orderProduct = orderProductRepository.findById(orderProductId);
		if (orderProduct.isPresent()) {
			return orderProduct.get();
		} else {
			throw new NotFoundException("No Product found for given id:" + orderProductId);
		}
	}

	/**
	 * This method is used to get topping if exist else throw NotFoundException
	 *
	 * @param id
	 * @return
	 */
	private Topping checkAndGetToppingIfExist(Long id) {
		Optional<Topping> toppingOptional = toppingRepository.findById(id);
		if (toppingOptional.isPresent()) {
			return toppingOptional.get();
		} else {
			throw new NotFoundException("No Topping found for given id:" + id);
		}
	}

	/**
	 * This method is used to get product if exist else throw NotFoundException
	 *
	 * @param id
	 * @return
	 */
	private Product checkAndGetProductIfExist(Long id) {
		Optional<Product> productOptional = productRepository.findById(id);
		if (productOptional.isPresent()) {
			return productOptional.get();
		} else {
			throw new NotFoundException("No Product found for given id:" + id);
		}
	}

	/**
	 * This method is used to create Topping
	 *
	 * @param orderProduct
	 * @param topping
	 * @return
	 */
	private OrderTopping createTopping(OrderProduct orderProduct, Topping topping) {
		OrderTopping orderTopping = new OrderTopping();
		orderTopping.setTopping(topping);
		orderTopping.setOrderProduct(orderProduct);
		return orderToppingRepository.save(orderTopping);
	}

	/**
	 * This method is used to get updated cart order and calculate amount and save into order cart table
	 *
	 * @param cartId
	 * @return
	 */
	@Override
	public Optional<OrderResponseDto> updateCart(Long cartId) {
		OrderCart orderCart = checkAndGetOrderCartIfExist(cartId);
		orderCart.setAmount(new BigDecimal(CalculationUtility.sum(orderCart.getOrderProducts()))
				.setScale(2, RoundingMode.DOWN));
		return Optional.ofNullable(orderMapper.entityToResponseDto(orderRepository.save(orderCart)));
	}

	/**
	 * This method is used for adding product into cart.
	 *
	 * @param cartId
	 * @param id
	 * @return
	 */
	private void addProduct(Long cartId, Long id) {
		OrderCart orderCart = checkAndGetOrderCartIfExist(cartId);
		Product product = checkAndGetProductIfExist(id);
		createProduct(orderCart, product);
	}

	/**
	 * This method is used for creating product
	 *
	 * @param orderCart
	 * @param product
	 */
	private void createProduct(OrderCart orderCart, Product product) {
		OrderProduct orderProduct = new OrderProduct();
		orderProduct.setOrderCart(orderCart);
		orderProduct.setProduct(product);
		orderProductRepository.save(orderProduct);
	}

	/**
	 * This method is used for removing topping from cart.
	 *
	 * @param cartId
	 * @param orderProductId
	 * @param id
	 * @return
	 */
	private void removeTopping(Long cartId, Long orderProductId, Long id) {
		checkAndGetOrderCartIfExist(cartId);
		checkAndGetOrderProductIfExist(orderProductId);
		orderToppingRepository.deleteById(id);
	}

	/**
	 * This method is used for removing product from cart.
	 *
	 * @param cartId
	 * @param id
	 * @return
	 */
	private void removeProduct(Long cartId, Long id) {
		checkAndGetOrderCartIfExist(cartId);
		orderProductRepository.deleteById(id);
	}

	/**
	 * This method is used for confirming order and return discounted amount if applicable.
	 *
	 * @param id
	 * @return
	 */
	@Override

	public Optional<OrderResponseDto> confirmOrder(Long id) {
		Optional<OrderCart> orderCartOptional = orderRepository.findById(id);
		if (orderCartOptional.isPresent()) {
			OrderCart orderCart = orderCartOptional.get();
			List<OrderProduct> orderProducts = orderCart.getOrderProducts();
			orderCart.setAmount(new BigDecimal(CalculationUtility.sum(orderProducts))
					.setScale(2, RoundingMode.DOWN));
			orderCart.setDiscount(new BigDecimal(CalculationUtility.discount(orderProducts))
					.setScale(2, RoundingMode.DOWN));
			OrderCart savedOrder = orderRepository.save(orderCart);
			return Optional.ofNullable(orderMapper.entityToResponseDto(savedOrder));
		}
		return Optional.empty();
	}

	/**
	 * This method is used for initializing cart and save user details.
	 *
	 * @param requestDto
	 * @return
	 */
	@Override

	public Optional<OrderResponseDto> initiateOrder(UserReqDto requestDto) {
		OrderCart orderCart = new OrderCart();
		orderCart.setActive(true);
		orderCart.setDate(new Date());
		orderCart.setUser(setUser(requestDto));
		orderCart.setAmount(new BigDecimal(0.0));
		orderCart.setDiscount(new BigDecimal(0.0));
		return Optional.ofNullable(orderMapper.entityToResponseDto(orderRepository.save(orderCart)));
	}

	/**
	 * This method is for map user request dto to entity.
	 *
	 * @param userReqDto
	 * @return
	 */
	private User setUser(UserReqDto userReqDto) {
		return userMapper.dtoToEntity(userReqDto);
	}

}
