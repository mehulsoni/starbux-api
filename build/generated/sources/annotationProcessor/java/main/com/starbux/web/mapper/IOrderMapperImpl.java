package com.starbux.web.mapper;

import com.starbux.dto.request.UserReqDto;
import com.starbux.dto.response.OrderProductResDto;
import com.starbux.dto.response.OrderResponseDto;
import com.starbux.dto.response.OrderToppingResDto;
import com.starbux.dto.response.ProductResDto;
import com.starbux.dto.response.ToppingResDto;
import com.starbux.entity.OrderCart;
import com.starbux.entity.OrderProduct;
import com.starbux.entity.OrderTopping;
import com.starbux.entity.Product;
import com.starbux.entity.Topping;
import com.starbux.entity.User;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2020-03-12T00:11:51+0200",
    comments = "version: 1.3.1.Final, compiler: javac, environment: Java 11.0.2 (Oracle Corporation)"
)
@Component
public class IOrderMapperImpl implements IOrderMapper {

    @Override
    public OrderResponseDto entityToResponseDto(OrderCart savedOrder) {
        if ( savedOrder == null ) {
            return null;
        }

        OrderResponseDto orderResponseDto = new OrderResponseDto();

        orderResponseDto.setId( savedOrder.getId() );
        orderResponseDto.setAmount( savedOrder.getAmount() );
        orderResponseDto.setDiscount( savedOrder.getDiscount() );
        orderResponseDto.setDate( savedOrder.getDate() );
        orderResponseDto.setUser( userToUserReqDto( savedOrder.getUser() ) );
        orderResponseDto.setOrderProducts( orderProductListToOrderProductResDtoList( savedOrder.getOrderProducts() ) );

        return orderResponseDto;
    }

    protected UserReqDto userToUserReqDto(User user) {
        if ( user == null ) {
            return null;
        }

        UserReqDto userReqDto = new UserReqDto();

        userReqDto.setFirstName( user.getFirstName() );
        userReqDto.setLastName( user.getLastName() );
        userReqDto.setEmail( user.getEmail() );
        userReqDto.setMobile( user.getMobile() );

        return userReqDto;
    }

    protected ProductResDto productToProductResDto(Product product) {
        if ( product == null ) {
            return null;
        }

        ProductResDto productResDto = new ProductResDto();

        productResDto.setId( product.getId() );
        productResDto.setName( product.getName() );
        productResDto.setCode( product.getCode() );
        productResDto.setPrice( product.getPrice() );
        productResDto.setEnabled( product.getEnabled() );

        return productResDto;
    }

    protected ToppingResDto toppingToToppingResDto(Topping topping) {
        if ( topping == null ) {
            return null;
        }

        ToppingResDto toppingResDto = new ToppingResDto();

        toppingResDto.setId( topping.getId() );
        toppingResDto.setName( topping.getName() );
        toppingResDto.setCode( topping.getCode() );
        toppingResDto.setPrice( topping.getPrice() );
        toppingResDto.setEnabled( topping.getEnabled() );

        return toppingResDto;
    }

    protected OrderToppingResDto orderToppingToOrderToppingResDto(OrderTopping orderTopping) {
        if ( orderTopping == null ) {
            return null;
        }

        OrderToppingResDto orderToppingResDto = new OrderToppingResDto();

        orderToppingResDto.setId( orderTopping.getId() );
        orderToppingResDto.setTopping( toppingToToppingResDto( orderTopping.getTopping() ) );

        return orderToppingResDto;
    }

    protected List<OrderToppingResDto> orderToppingListToOrderToppingResDtoList(List<OrderTopping> list) {
        if ( list == null ) {
            return null;
        }

        List<OrderToppingResDto> list1 = new ArrayList<OrderToppingResDto>( list.size() );
        for ( OrderTopping orderTopping : list ) {
            list1.add( orderToppingToOrderToppingResDto( orderTopping ) );
        }

        return list1;
    }

    protected OrderProductResDto orderProductToOrderProductResDto(OrderProduct orderProduct) {
        if ( orderProduct == null ) {
            return null;
        }

        OrderProductResDto orderProductResDto = new OrderProductResDto();

        orderProductResDto.setId( orderProduct.getId() );
        orderProductResDto.setProduct( productToProductResDto( orderProduct.getProduct() ) );
        orderProductResDto.setOrderToppings( orderToppingListToOrderToppingResDtoList( orderProduct.getOrderToppings() ) );

        return orderProductResDto;
    }

    protected List<OrderProductResDto> orderProductListToOrderProductResDtoList(List<OrderProduct> list) {
        if ( list == null ) {
            return null;
        }

        List<OrderProductResDto> list1 = new ArrayList<OrderProductResDto>( list.size() );
        for ( OrderProduct orderProduct : list ) {
            list1.add( orderProductToOrderProductResDto( orderProduct ) );
        }

        return list1;
    }
}
