package com.urjc.mca.tema12.p2.dto;

import lombok.Data;

@Data
public class MyOrderDto {

    private long id = -1;

    private CustomerDto customer;

    private ProductDto product;

    private int quantity;

    private int totalPrice;
}
