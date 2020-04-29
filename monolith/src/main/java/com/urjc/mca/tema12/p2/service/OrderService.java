package com.urjc.mca.tema12.p2.service;

import com.urjc.mca.tema12.p2.dto.CustomerDto;
import com.urjc.mca.tema12.p2.dto.MyOrderDto;
import com.urjc.mca.tema12.p2.dto.ProductDto;
import com.urjc.mca.tema12.p2.mapper.MyOrderMapper;
import com.urjc.mca.tema12.p2.mapper.ProductMapper;
import com.urjc.mca.tema12.p2.model.MyOrder;
import com.urjc.mca.tema12.p2.model.Product;
import com.urjc.mca.tema12.p2.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private CustomerService customerService;

    @Transactional
    public MyOrderDto save(MyOrderDto myOrderDto) throws Exception {
        if (checkStock(myOrderDto) &&
                checkCreditLimit(myOrderDto))
            return createMyOrder(myOrderDto);
        else
            throw new Exception();
    }

    private MyOrderDto createMyOrder(MyOrderDto myOrderDto) {
        customerService.save(myOrderDto.getCustomer());
        productService.save(myOrderDto.getProduct());
        MyOrder myOrder = convertBasicPostDTOToPostEntity(myOrderDto);
        return convertMyOrderToMyOrderDto(orderRepository.save(myOrder));
    }

    private boolean checkCreditLimit(MyOrderDto myOrderDto) {
        CustomerDto customer = customerService.getCustomer(myOrderDto.getCustomer().getId());
        if (customer.getCreditLimit() >= myOrderDto.getTotalPrice()) {
            customer.setCreditLimit(customer.getCreditLimit() - myOrderDto.getTotalPrice());
            myOrderDto.setCustomer(customer);
            return true;
        }
        return false;
    }

    private boolean checkStock(MyOrderDto myOrderDto) {
        ProductDto product = productService.getProduct(myOrderDto.getProduct().getId());
        if (product.getStock() >= myOrderDto.getQuantity()) {
            product.setStock(product.getStock() - myOrderDto.getQuantity());
            myOrderDto.setProduct(product);
            return true;
        }
        return false;
    }

    public MyOrderDto getOrder(long id) {
        return convertMyOrderToMyOrderDto(orderRepository.findById(id).orElseThrow(EntityNotFoundException::new));
    }

    public List<MyOrderDto> getOrders() {
        return orderRepository.findAll().stream().map(myOrder -> convertMyOrderToMyOrderDto(myOrder)).collect(Collectors.toList());
    }


    private MyOrderDto convertMyOrderToMyOrderDto(MyOrder myOrder) {
        return MyOrderMapper.MAPPER.toBasicMyOrderDto(myOrder);
    }

    private MyOrder convertBasicPostDTOToPostEntity(MyOrderDto producDto) {
        return MyOrderMapper.MAPPER.toMyOrder(producDto);
    }
}
