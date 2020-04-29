package com.urjc.mca.tema12.p2.dto;

import lombok.Data;


@Data
public class CustomerDto {

    private long id = -1;

    private String name;

    private int creditLimit;
}
