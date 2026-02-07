package com.codigenics.ecommerce_app.service;


import com.codigenics.ecommerce_app.payload.ProductRequest;
import com.codigenics.ecommerce_app.payload.ProductResponse;
import com.codigenics.ecommerce_app.payload.ProductResponseList;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ProductService {
     ProductResponse addProduct(ProductRequest productRequest, Long categoryId);

     ProductResponseList getAllProducts(Integer pageNumber , Integer pageSize);

    ProductResponseList getByCategoryId ( Long categoryId, Integer pageNumber, Integer pageSize);

    ProductResponseList getProductByKeyword(String keyword);

    ProductResponse updateProduct(Long productId , ProductRequest productRequest);

    ProductResponse deletedProductById(Long productId);

    ProductResponse updateProductImage(Long productId, MultipartFile image) throws IOException;
}
