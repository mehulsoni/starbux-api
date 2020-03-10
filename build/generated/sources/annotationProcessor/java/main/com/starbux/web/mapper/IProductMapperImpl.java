package com.starbux.web.mapper;

import com.starbux.dto.request.ProductReqDto;
import com.starbux.dto.response.ProductResDto;
import com.starbux.entity.Product;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2020-03-10T20:35:02+0200",
    comments = "version: 1.3.1.Final, compiler: javac, environment: Java 11.0.2 (Oracle Corporation)"
)
@Component
public class IProductMapperImpl implements IProductMapper {

    @Override
    public Product requestDtoToEntity(ProductReqDto productReqDto) {
        if ( productReqDto == null ) {
            return null;
        }

        Product product = new Product();

        product.setId( productReqDto.getId() );
        product.setName( productReqDto.getName() );
        product.setCode( productReqDto.getCode() );
        product.setPrice( productReqDto.getPrice() );
        product.setEnabled( productReqDto.getEnabled() );

        return product;
    }

    @Override
    public ProductResDto entityToResponseDto(Product product) {
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

    @Override
    public List<ProductResDto> entitiesToResponseDtoList(List<Product> products) {
        if ( products == null ) {
            return null;
        }

        List<ProductResDto> list = new ArrayList<ProductResDto>( products.size() );
        for ( Product product : products ) {
            list.add( entityToResponseDto( product ) );
        }

        return list;
    }
}
