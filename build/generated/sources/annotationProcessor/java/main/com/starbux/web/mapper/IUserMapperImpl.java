package com.starbux.web.mapper;

import com.starbux.dto.request.UserReqDto;
import com.starbux.entity.OrderPerCustomerReportDto;
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
public class IUserMapperImpl implements IUserMapper {

    @Override
    public User dtoToEntity(UserReqDto userReqDto) {
        if ( userReqDto == null ) {
            return null;
        }

        User user = new User();

        user.setFirstName( userReqDto.getFirstName() );
        user.setLastName( userReqDto.getLastName() );
        user.setEmail( userReqDto.getEmail() );
        user.setMobile( userReqDto.getMobile() );

        return user;
    }

    @Override
    public OrderPerCustomerReportDto entityToOrderPerCustomerReportDto(User user) {
        if ( user == null ) {
            return null;
        }

        OrderPerCustomerReportDto orderPerCustomerReportDto = new OrderPerCustomerReportDto();

        if ( user.getId() != null ) {
            orderPerCustomerReportDto.setId( user.getId().intValue() );
        }
        orderPerCustomerReportDto.setEmail( user.getEmail() );

        return orderPerCustomerReportDto;
    }

    @Override
    public List<OrderPerCustomerReportDto> entityToOrderPerCustomerReportDtoList(List<User> users) {
        if ( users == null ) {
            return null;
        }

        List<OrderPerCustomerReportDto> list = new ArrayList<OrderPerCustomerReportDto>( users.size() );
        for ( User user : users ) {
            list.add( entityToOrderPerCustomerReportDto( user ) );
        }

        return list;
    }
}
