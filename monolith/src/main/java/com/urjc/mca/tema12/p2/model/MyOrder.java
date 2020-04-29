package com.urjc.mca.tema12.p2.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class MyOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id = -1;

    @OneToOne
    private Customer customer;

    @OneToOne
    private Product product;

    private int quantity;

    private int totalPrice;
}
