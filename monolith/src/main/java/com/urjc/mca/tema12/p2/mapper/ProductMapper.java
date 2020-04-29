package com.urjc.mca.tema12.p2.mapper;

import com.urjc.mca.tema12.p2.dto.ProductDto;
import com.urjc.mca.tema12.p2.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProductMapper {

    ProductMapper MAPPER = Mappers.getMapper(ProductMapper.class);

    ProductDto toBasicProductDto(Product customer);

    Product toProduct(ProductDto customerDto);
}
