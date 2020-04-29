package com.urjc.mca.tema12.p2.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ProductControllerTest {


    @Autowired
    private MockMvc mvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    @DisplayName("Crear un producto y comprobar que se ha creado correctamente")
    public void creatProduct() throws Exception {

        ProductDto product = new ProductDto();
        product.setPrice(10);
        product.setStock(20);
        product.setName("airPods");

        MvcResult result = mvc.perform(
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

        mvc.perform(
                get("/product/" + resultProduct.getId())
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(jsonPath("$.name", equalTo(product.getName())))
                .andExpect(jsonPath("$.price", equalTo(product.getPrice())))
                .andExpect(jsonPath("$.stock", equalTo(product.getStock())));

    }


}