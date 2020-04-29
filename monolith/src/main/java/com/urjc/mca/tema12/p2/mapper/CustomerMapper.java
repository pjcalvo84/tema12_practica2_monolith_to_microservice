package com.urjc.mca.tema12.p2.mapper;

import com.urjc.mca.tema12.p2.dto.CustomerDto;
import com.urjc.mca.tema12.p2.model.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CustomerMapper {

    CustomerMapper MAPPER = Mappers.getMapper(CustomerMapper.class);

    CustomerDto toBasicCustomerDto(Customer customer);

    Customer toCustomer(CustomerDto customerDto);
}
