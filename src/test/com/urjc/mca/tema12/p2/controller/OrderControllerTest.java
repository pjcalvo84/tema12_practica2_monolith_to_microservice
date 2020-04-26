package com.urjc.mca.tema12.p2.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.urjc.mca.tema12.p2.dto.CustomerDto;
import com.urjc.mca.tema12.p2.dto.MyOrderDto;
import com.urjc.mca.tema12.p2.dto.ProductDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class OrderControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    @DisplayName("Crear una orden y comprobado que se ha creado correctamente y se ha ajustado el stock y el saldo del cliente")
    public void createOrder() throws Exception {

        CustomerDto customer = new CustomerDto();
        customer.setCreditLimit(10);
        customer.setName("Pablo");

        MvcResult result = mvc.perform(
                post("/customer/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customer))
        )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", equalTo(customer.getName())))
                .andExpect(jsonPath("$.creditLimit", equalTo(customer.getCreditLimit())))

                .andReturn();

        CustomerDto resultCustomer = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                CustomerDto.class
        );

        ProductDto product = new ProductDto();
        product.setPrice(10);
        product.setStock(20);
        product.setName("airPods");

        result = mvc.perform(
                post("/product/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(product))
        )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", equalTo(product.getName())))
                .andExpect(jsonPath("$.price", equalTo(product.getPrice())))
                .andExpect(jsonPath("$.stock", equalTo(product.getStock())))

                .andReturn();

        ProductDto resultProduct = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                ProductDto.class
        );

        MyOrderDto myOrder = new MyOrderDto();
        myOrder.setCustomer(resultCustomer);
        myOrder.setProduct(resultProduct);
        myOrder.setQuantity(5);
        myOrder.setTotalPrice(7);

        result = mvc.perform(
                post("/order/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(myOrder))
        )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.totalPrice", equalTo(myOrder.getTotalPrice())))
                .andExpect(jsonPath("$.quantity", equalTo(myOrder.getQuantity())))
                .andExpect(jsonPath("$.customer.id", equalTo((int) myOrder.getCustomer().getId())))
                .andExpect(jsonPath("$.customer.name", equalTo(myOrder.getCustomer().getName())))
                .andExpect(jsonPath("$.customer.creditLimit", equalTo(myOrder.getCustomer().getCreditLimit() - myOrder.getTotalPrice())))
                .andExpect(jsonPath("$.product.id", equalTo((int) myOrder.getProduct().getId())))
                .andExpect(jsonPath("$.product.name", equalTo(myOrder.getProduct().getName())))
                .andExpect(jsonPath("$.product.stock", equalTo(myOrder.getProduct().getStock() - myOrder.getQuantity())))
                .andReturn();

        MyOrderDto resultMyOrder = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                MyOrderDto.class
        );

        mvc.perform(
                get("/order/" + resultMyOrder.getId())
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(jsonPath("$.totalPrice", equalTo(resultMyOrder.getTotalPrice())))
                .andExpect(jsonPath("$.quantity", equalTo(resultMyOrder.getQuantity())))
                .andExpect(jsonPath("$.customer.id", equalTo((int) resultMyOrder.getCustomer().getId())))
                .andExpect(jsonPath("$.customer.name", equalTo(resultMyOrder.getCustomer().getName())))
                .andExpect(jsonPath("$.customer.creditLimit", equalTo(customer.getCreditLimit() - myOrder.getTotalPrice())))
                .andExpect(jsonPath("$.product.id", equalTo((int) resultMyOrder.getProduct().getId())))
                .andExpect(jsonPath("$.product.name", equalTo(resultMyOrder.getProduct().getName())))
                .andExpect(jsonPath("$.product.stock", equalTo(product.getStock() - myOrder.getQuantity())));

    }


    @Test
    @DisplayName("No se crea una orden porque el cliente no tiene saldo")
    public void createOrderWithoutCredit() throws Exception {

        CustomerDto customer = new CustomerDto();
        customer.setCreditLimit(10);
        customer.setName("Pablo");

        MvcResult result = mvc.perform(
                post("/customer/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customer))
        )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", equalTo(customer.getName())))
                .andExpect(jsonPath("$.creditLimit", equalTo(customer.getCreditLimit())))

                .andReturn();

        CustomerDto resultCustomer = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                CustomerDto.class
        );

        ProductDto product = new ProductDto();
        product.setPrice(10);
        product.setStock(20);
        product.setName("airPods");

        result = mvc.perform(
                post("/product/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(product))
        )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", equalTo(product.getName())))
                .andExpect(jsonPath("$.price", equalTo(product.getPrice())))
                .andExpect(jsonPath("$.stock", equalTo(product.getStock())))

                .andReturn();

        ProductDto resultProduct = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                ProductDto.class
        );

        MyOrderDto myOrder = new MyOrderDto();
        myOrder.setCustomer(resultCustomer);
        myOrder.setProduct(resultProduct);
        myOrder.setQuantity(35);
        myOrder.setTotalPrice(17);

        mvc.perform(
                post("/order/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(myOrder))
        )
                .andExpect(status().isPreconditionFailed());

        mvc.perform(
                get("/customer/" + resultCustomer.getId())
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(jsonPath("$.creditLimit", equalTo(resultCustomer.getCreditLimit())));

    }

    @Test
    @DisplayName("No se crea una orden porque no hay stock del producto")
    public void createOrderWithoutStock() throws Exception {

        CustomerDto customer = new CustomerDto();
        customer.setCreditLimit(10);
        customer.setName("Pablo");

        MvcResult result = mvc.perform(
                post("/customer/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customer))
        )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", equalTo(customer.getName())))
                .andExpect(jsonPath("$.creditLimit", equalTo(customer.getCreditLimit())))

                .andReturn();

        CustomerDto resultCustomer = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                CustomerDto.class
        );

        ProductDto product = new ProductDto();
        product.setPrice(10);
        product.setStock(20);
        product.setName("airPods");

        result = mvc.perform(
                post("/product/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(product))
        )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", equalTo(product.getName())))
                .andExpect(jsonPath("$.price", equalTo(product.getPrice())))
                .andExpect(jsonPath("$.stock", equalTo(product.getStock())))

                .andReturn();

        ProductDto resultProduct = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                ProductDto.class
        );

        MyOrderDto myOrder = new MyOrderDto();
        myOrder.setCustomer(resultCustomer);
        myOrder.setProduct(resultProduct);
        myOrder.setQuantity(35);
        myOrder.setTotalPrice(17);

        mvc.perform(
                post("/order/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(myOrder))
        )
                .andExpect(status().isPreconditionFailed());

        mvc.perform(
                get("/product/" + resultProduct.getId())
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(jsonPath("$.stock", equalTo(resultProduct.getStock())));

    }
}



