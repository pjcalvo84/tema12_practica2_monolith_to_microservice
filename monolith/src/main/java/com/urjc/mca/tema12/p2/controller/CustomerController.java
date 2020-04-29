package com.urjc.mca.tema12.p2.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.urjc.mca.tema12.p2.dto.CustomerDto;
import com.urjc.mca.tema12.p2.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    CustomerService customerService;

    @Autowired
    ObjectMapper objectMapper;

    @GetMapping("/")
    public ResponseEntity<List<CustomerDto>> listCustomer() {
        List<CustomerDto> myList = this.customerService.getCustomers();
        return new ResponseEntity<>(myList, HttpStatus.OK);
    }


    @GetMapping("/{id}")
    public ResponseEntity<CustomerDto> getCustomer(@PathVariable long id) {
        CustomerDto customer = this.customerService.getCustomer(id);
        if (customer == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(customer, HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<CustomerDto> newCustomer(@RequestBody CustomerDto customer) {
        CustomerDto newCustomer = this.customerService.save(customer);
        return new ResponseEntity<>(newCustomer, HttpStatus.CREATED);
    }

    @PutMapping("/{id}/credit/{credit}")
    public ResponseEntity<CustomerDto> addCredit(@PathVariable long id, @PathVariable int credit) {
        CustomerDto customerDto = this.customerService.addCredit(id, credit);

        return new ResponseEntity<>(customerDto, HttpStatus.CREATED);
    }

    @GetMapping("/{id}/credit/{credit}")
    public ResponseEntity<Boolean> haveCredit(@PathVariable long id, @PathVariable int credit) {
        return new ResponseEntity<>(customerService.haveCredit(id, credit), HttpStatus.OK);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<CustomerDto> updateCredit(@PathVariable long id, @RequestBody Map<String, String> operation) {
        try {
            CustomerDto customerDto = customerService.updateCredit(id, operation);
            return new ResponseEntity<>(customerDto, HttpStatus.OK);
        } catch (IllegalArgumentException ex) {
            return new ResponseEntity<>(HttpStatus.PRECONDITION_FAILED);
        }
    }
}
