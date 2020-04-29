package com.urjc.mca.tema12.p2.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
public class ProductDto {

    @Schema(example = "-1")
    private long id = -1;

    @Schema(example = "airPods")
    private String name;

    @Schema(example = "12")
    private int stock;

    @Schema(example = "6")
    private int price;

}
