package com.starbux.web.mapper;

import com.starbux.dto.request.ToppingReqDto;
import com.starbux.dto.response.ToppingResDto;
import com.starbux.entity.Topping;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2020-03-10T22:45:33+0200",
    comments = "version: 1.3.1.Final, compiler: javac, environment: Java 11.0.2 (Oracle Corporation)"
)
@Component
public class IToppingMapperImpl implements IToppingMapper {

    @Override
    public Topping requestDtoToEntity(ToppingReqDto toppingReqDto) {
        if ( toppingReqDto == null ) {
            return null;
        }

        Topping topping = new Topping();

        topping.setId( toppingReqDto.getId() );
        topping.setName( toppingReqDto.getName() );
        topping.setCode( toppingReqDto.getCode() );
        topping.setPrice( toppingReqDto.getPrice() );
        topping.setEnabled( toppingReqDto.getEnabled() );

        return topping;
    }

    @Override
    public ToppingResDto entityToResponseDto(Topping savedTopping) {
        if ( savedTopping == null ) {
            return null;
        }

        ToppingResDto toppingResDto = new ToppingResDto();

        toppingResDto.setId( savedTopping.getId() );
        toppingResDto.setName( savedTopping.getName() );
        toppingResDto.setCode( savedTopping.getCode() );
        toppingResDto.setPrice( savedTopping.getPrice() );
        toppingResDto.setEnabled( savedTopping.getEnabled() );

        return toppingResDto;
    }

    @Override
    public List<ToppingResDto> entitiesToResponseDtoList(List<Topping> toppings) {
        if ( toppings == null ) {
            return null;
        }

        List<ToppingResDto> list = new ArrayList<ToppingResDto>( toppings.size() );
        for ( Topping topping : toppings ) {
            list.add( entityToResponseDto( topping ) );
        }

        return list;
    }
}
