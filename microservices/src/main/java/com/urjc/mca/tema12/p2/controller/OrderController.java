package com.urjc.mca.tema12.p2.controller;

import com.urjc.mca.tema12.p2.dto.CustomerDto;
import com.urjc.mca.tema12.p2.dto.MyOrderDto;
import com.urjc.mca.tema12.p2.feign.CustomerClient;
import com.urjc.mca.tema12.p2.model.MyOrder;
import com.urjc.mca.tema12.p2.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    OrderService orderService;


    @GetMapping("/")
    public ResponseEntity<List<MyOrderDto>> listOrder() {
        List<MyOrderDto> myList = this.orderService.getOrders();
        return new ResponseEntity<>(myList, HttpStatus.OK);
    }


    @GetMapping("/{id}")
    public ResponseEntity<MyOrderDto> getOrder(@PathVariable long id) {
        MyOrderDto myOrder = this.orderService.getOrder(id);
        if (myOrder == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(myOrder, HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<MyOrderDto> newOrder(@RequestBody MyOrderDto myOrder) throws Exception {

        try {
            MyOrderDto newMyOrder = this.orderService.save(myOrder);
            return new ResponseEntity<>(newMyOrder, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.PRECONDITION_FAILED);
        }
    }
}
