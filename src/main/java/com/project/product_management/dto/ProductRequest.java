package com.project.product_management.dto;


import jakarta.persistence.Entity;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductRequest {

    @NotBlank(message = "sku is mandatory")
    private String sku;

    @NotBlank(message = "name is mandatory")
    private String name;

    private String description;

    @NotNull(message = "price is mandatory")
    @DecimalMin(value = "0.0", inclusive = false, message = "price must be greater than 0")
    private BigDecimal price;


    @NotBlank(message = "category is mandatory")
    private String category;

    @NotNull(message = "stock quantity is mandatory")
    @Min(value = 0, message = "stock quantity can't be 0")
    private Integer stockQty;
}
