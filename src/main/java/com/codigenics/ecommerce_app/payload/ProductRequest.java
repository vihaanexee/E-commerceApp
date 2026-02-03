package com.codigenics.ecommerce_app.payload;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequest {

    private Long productId;
    private String productName;
    private String description;
    private String image;
    private Integer quantity;
    private BigDecimal price;
    private BigDecimal discount;
    private BigDecimal specialPrice;
}
