package com.urjc.mca.tema12.p2.repository;

import com.urjc.mca.tema12.p2.model.MyOrder;
import org.springframework.data.jpa.repository.JpaRepository;


public interface OrderRepository extends JpaRepository<MyOrder, Long> {

}