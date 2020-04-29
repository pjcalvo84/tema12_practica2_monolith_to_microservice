package com.urjc.mca.tema12.p2.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;


@Data
public class CustomerDto {

    @Schema(example = "-1")
    private long id = -1;

    @Schema(example = "Pablo")
    private String name;

    @Schema(example = "10")
    private int creditLimit;
}
