package com.urjc.mca.tema12.p2.service;

import com.urjc.mca.tema12.p2.dto.MyOrderDto;
import com.urjc.mca.tema12.p2.feign.CustomerClient;
import com.urjc.mca.tema12.p2.feign.ProductClient;
import com.urjc.mca.tema12.p2.mapper.MyOrderMapper;
import com.urjc.mca.tema12.p2.model.MyOrder;
import com.urjc.mca.tema12.p2.repository.OrderRepository;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class OrderService {


    private static final Logger log = LoggerFactory.getLogger(OrderService.class);

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    CustomerClient customerClient;

    @Autowired
    ProductClient productClient;

    @Transactional
    public MyOrderDto save(MyOrderDto myOrderDto) throws Exception {

        if (checkStock(myOrderDto) &&
                checkCreditLimit(myOrderDto))
            return createMyOrder(myOrderDto);
        else
            throw new Exception();
    }

    private MyOrderDto createMyOrder(MyOrderDto myOrderDto) {

        updateCreditLimit(myOrderDto);
        updateStock(myOrderDto);

        MyOrder myOrder = convertBasicPostDTOToPostEntity(myOrderDto);
        return convertMyOrderToMyOrderDto(orderRepository.save(myOrder));
    }

    private void updateStock(MyOrderDto myOrderDto) {
        Map<String, String> operator;
        operator = new HashMap<>();
        operator.put("operation","subtract");
        operator.put("stock", ""+myOrderDto.getQuantity());

        try{
            productClient.updateStock(myOrderDto.getProductId(), operator);
        }
        catch (FeignException.FeignClientException ex){
            operator = new HashMap<>();
            operator.put("operation","add");
            operator.put("amount", ""+myOrderDto.getTotalPrice());
            customerClient.updateCredit(myOrderDto.getCustomerId(), operator);
            throw new IllegalArgumentException();
        }
    }

    private void updateCreditLimit(MyOrderDto myOrderDto) {
        Map<String, String> operator = new HashMap<>();
        operator.put("operation","subtract");
        operator.put("amount", ""+myOrderDto.getQuantity());
        try {
            customerClient.updateCredit(myOrderDto.getCustomerId(), operator);
        }
        catch (FeignException.FeignClientException ex){
            throw new IllegalArgumentException();
        }
    }

    private boolean checkCreditLimit(MyOrderDto myOrderDto) {
        log.info("customerClient:" + customerClient.toString());

        return customerClient.haveCredit(myOrderDto.getCustomerId(), myOrderDto.getTotalPrice());
    }

    private boolean checkStock(MyOrderDto myOrderDto) {
        log.info("productClient:" + productClient.toString());

        return productClient.haveStock(myOrderDto.getProductId(), myOrderDto.getQuantity());
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
