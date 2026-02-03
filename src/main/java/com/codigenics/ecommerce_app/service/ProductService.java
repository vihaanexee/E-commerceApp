package com.codigenics.ecommerce_app.service;


import com.codigenics.ecommerce_app.payload.ProductRequest;
import com.codigenics.ecommerce_app.payload.ProductResponse;
import com.codigenics.ecommerce_app.payload.ProductResponseList;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

public interface ProductService {
     ProductResponse addProduct(ProductRequest productRequest, Long categoryId);

     ProductResponseList getAllProducts(Integer pageNumber , Integer pageSize);
}
