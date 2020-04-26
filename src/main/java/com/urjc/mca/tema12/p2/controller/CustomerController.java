package com.urjc.mca.tema12.p2.controller;

import com.urjc.mca.tema12.p2.dto.CustomerDto;
import com.urjc.mca.tema12.p2.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    private static final Logger log = LoggerFactory.getLogger(CustomerController.class);

    @Autowired
    CustomerService customerService;

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

    @PostMapping("/{id}/credit/{credit}")
    public ResponseEntity<CustomerDto> addCredit(@PathVariable long id, @PathVariable int credit) {
        CustomerDto customerDto = this.customerService.addCredit(id, credit);
        log.info(credit + " credit added to " + customerDto.getName());
        return new ResponseEntity<>(customerDto, HttpStatus.CREATED);
    }


}
