package com.urjc.mca.tema12.p2.dto;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
public class ProductDto {

    private long id = -1;

    private String name;

    private int stock;

    private int price;

}
