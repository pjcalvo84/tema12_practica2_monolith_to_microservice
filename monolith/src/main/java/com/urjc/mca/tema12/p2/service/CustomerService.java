package com.urjc.mca.tema12.p2.service;

import com.urjc.mca.tema12.p2.dto.CustomerDto;
import com.urjc.mca.tema12.p2.mapper.CustomerMapper;
import com.urjc.mca.tema12.p2.model.Customer;
import com.urjc.mca.tema12.p2.repository.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CustomerService {


    private static final Logger log = LoggerFactory.getLogger(CustomerService.class);

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
        sendNotification(customer.getId(), credit);

        return convertCustomerToCustomerDto(customerRepository.save(customer));

    }

    private CustomerDto convertCustomerToCustomerDto(Customer customer) {
        return CustomerMapper.MAPPER.toBasicCustomerDto(customer);
    }

    private Customer convertCustomerDtoToCustomer(CustomerDto customerDto) {
        return CustomerMapper.MAPPER.toCustomer(customerDto);
    }

    public boolean haveCredit(long id, int credit) {
        Customer customer = customerRepository.findById(id).orElseThrow(EntityNotFoundException::new);

        return customer.getCreditLimit() >= credit;
    }

    public CustomerDto updateCredit(long id, Map<String, String> operation) throws IllegalArgumentException {
        Customer customer = customerRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        processUpdateCredit(customer, operation);
        return convertCustomerToCustomerDto(customerRepository.save(customer));
    }

    private void processUpdateCredit(Customer customer, Map<String, String> operation) throws IllegalArgumentException {
        if (operation.get("operation").equals("add")) {
            customer.setCreditLimit(customer.getCreditLimit() + getAmount(operation, "amount"));
            sendNotification(customer.getId(), getAmount(operation, "amount"));

        } else if (operation.get("operation").equals("subtract")) {
            if (customer.getCreditLimit() > getAmount(operation, "amount"))
                customer.setCreditLimit(customer.getCreditLimit() - getAmount(operation, "amount"));
            else
                throw new IllegalArgumentException();
        } else
            throw new IllegalArgumentException();
    }

    private int getAmount(Map<String, String> operation, String amount) {
        return Integer.parseInt(operation.get(amount));
    }


    private void sendNotification(long id, int quantity) {
        log.info(quantity + " credit added to " + id);

    }
}
