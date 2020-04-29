package com.urjc.mca.tema12.p2.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class MyOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id = -1;

    private long productId = -1;

    private long customerId = -1;

    private int quantity;

    private int totalPrice;
}
