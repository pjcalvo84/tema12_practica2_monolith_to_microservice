package com.urjc.mca.tema12.p2.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class MyOrderDto {

    @Schema(example = "-1")
    private long id = -1;


    private CustomerDto customer;

    private ProductDto product;


    private int quantity;

    private int totalPrice;
}
