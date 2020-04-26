package com.urjc.mca.tema12.p2.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id = -1;

    private String name;

    private int creditLimit;

    public void addCredit(int creditLimit) {
        this.creditLimit += creditLimit;
    }
}
