package com.urjc.mca.tema12.p2.controller;

import static org.hamcrest.CoreMatchers.equalTo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.urjc.mca.tema12.p2.dto.CustomerDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CustomerControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    public void createCustomer() throws Exception {

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

        mvc.perform(
                get("/customer/" + resultCustomer.getId())
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(jsonPath("$.name", equalTo(customer.getName())))
                .andExpect(jsonPath("$.creditLimit", equalTo(customer.getCreditLimit())));

    }

    @Test
    public void addCredit() throws Exception {

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

        int credit = 12;

        mvc.perform(
                post("/customer/" + resultCustomer.getId() + "/credit/" + credit)
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(jsonPath("$.creditLimit", equalTo(resultCustomer.getCreditLimit() + credit)));

    }
}