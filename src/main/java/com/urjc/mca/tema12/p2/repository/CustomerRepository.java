package com.urjc.mca.tema12.p2.repository;

import com.urjc.mca.tema12.p2.model.Customer;
import com.urjc.mca.tema12.p2.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CustomerRepository extends JpaRepository<Customer, Long> {

}