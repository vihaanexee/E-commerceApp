package com.codigenics.ecommerce_app.controller;


import com.codigenics.ecommerce_app.config.AppConstants;
import com.codigenics.ecommerce_app.payload.ProductRequest;
import com.codigenics.ecommerce_app.payload.ProductResponse;
import com.codigenics.ecommerce_app.payload.ProductResponseList;
import com.codigenics.ecommerce_app.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api")
public class ProductController {
    @Autowired
    private ProductService productService;

    @PostMapping("/admin/categories/{categoryId}/addProduct")
    public ResponseEntity<ProductResponse>  addProduct(@Valid @RequestBody ProductRequest productRequest , @PathVariable Long categoryId){
        ProductResponse productResponse = productService.addProduct(productRequest, categoryId);
        return new ResponseEntity<>(productResponse ,HttpStatus.CREATED);
    }


    @GetMapping("/public/getAllProducts")
    public ResponseEntity<ProductResponseList> getAllProducts(
            @RequestParam(name = "pageNumber" , defaultValue = AppConstants.PAGE_NUMBER , required = false) Integer pageNumber,
            @RequestParam(name = "pageSize" , defaultValue =  AppConstants.PAGE_SIZE , required = false) Integer pageSize){
        ProductResponseList productResponseList = productService.getAllProducts(pageNumber , pageSize);
        return ResponseEntity.ok(productResponseList);
    }

    @GetMapping("/public/categories/{categoryId}/products")
    public ResponseEntity<ProductResponseList> getProductByCategoryId(
            @PathVariable Long categoryId,
            @RequestParam(name = "pageNumber" , defaultValue = AppConstants.PAGE_NUMBER , required = false) Integer pageNumber,
            @RequestParam(name = "pageSize" , defaultValue = AppConstants.PAGE_SIZE , required = false)Integer pageSize) {
        ProductResponseList productResponseList = productService.getByCategoryId(categoryId, pageNumber, pageSize);
        return new ResponseEntity<>(productResponseList, HttpStatus.OK);
    }

    @GetMapping("/public/products/keyword/{keyword}")
    public ResponseEntity<ProductResponseList> getProductByKeyword( @PathVariable String keyword){
        ProductResponseList productResponseList = productService.getProductByKeyword(keyword);
        return new ResponseEntity<>(productResponseList , HttpStatus.FOUND);
    }

    @PutMapping("/products/{productId}")
        public ResponseEntity<ProductResponse> updateProduct(@PathVariable Long productId , @Valid @RequestBody ProductRequest productRequest){
        ProductResponse productResponse = productService.updateProduct(productId , productRequest);
        return ResponseEntity.ok(productResponse);
    }

    @DeleteMapping("/products/{productId}")
    public ResponseEntity<ProductResponse> deleteProduct(@PathVariable Long productId){
        ProductResponse productResponse = productService.deletedProductById(productId);
        return new ResponseEntity<>(productResponse , HttpStatus.OK);
    }

    @PutMapping("/products/{productId}/image")
    public ResponseEntity<ProductResponse> updateProductImage(@PathVariable Long productId ,
                                                              @RequestParam(name = "image")MultipartFile image) throws IOException {
        ProductResponse productResponse = productService.updateProductImage(productId , image);
        return new ResponseEntity<>(productResponse ,HttpStatus.OK);
    }

}
