package com.urjc.mca.tema12.p2.mapper;

import com.urjc.mca.tema12.p2.dto.MyOrderDto;
import com.urjc.mca.tema12.p2.dto.ProductDto;
import com.urjc.mca.tema12.p2.model.MyOrder;
import com.urjc.mca.tema12.p2.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MyOrderMapper {

    MyOrderMapper MAPPER = Mappers.getMapper(MyOrderMapper.class);

    MyOrderDto toBasicMyOrderDto(MyOrder order);

    MyOrder toMyOrder(MyOrderDto orderDto);
}
