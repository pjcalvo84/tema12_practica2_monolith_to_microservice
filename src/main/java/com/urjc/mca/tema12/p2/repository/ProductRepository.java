package com.urjc.mca.tema12.p2.repository;

import com.urjc.mca.tema12.p2.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProductRepository extends JpaRepository<Product, Long> {


}