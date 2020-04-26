package com.urjc.mca.tema12.p2.service;

import com.urjc.mca.tema12.p2.dto.CustomerDto;
import com.urjc.mca.tema12.p2.mapper.CustomerMapper;
import com.urjc.mca.tema12.p2.model.Customer;
import com.urjc.mca.tema12.p2.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public CustomerDto save(CustomerDto customerDto) {
        Customer customer = convertCustomerDtoToCustomer(customerDto);
        return convertCustomerToCustomerDto(customerRepository.save(customer));
    }

    public CustomerDto getCustomer(long id) {
        return convertCustomerToCustomerDto(customerRepository.findById(id).orElseThrow(EntityNotFoundException::new));
    }

    public List<CustomerDto> getCustomers() {
        return customerRepository.findAll().stream().map(customer -> convertCustomerToCustomerDto(customer)).collect(Collectors.toList());
    }

    public CustomerDto addCredit(long id, int credit) {
        Customer customer = customerRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        customer.addCredit(credit);
        return convertCustomerToCustomerDto(customerRepository.save(customer));

    }

    private CustomerDto convertCustomerToCustomerDto(Customer customer) {
        return CustomerMapper.MAPPER.toBasicCustomerDto(customer);
    }

    private Customer convertCustomerDtoToCustomer(CustomerDto customerDto) {
        return CustomerMapper.MAPPER.toCustomer(customerDto);
    }

}
