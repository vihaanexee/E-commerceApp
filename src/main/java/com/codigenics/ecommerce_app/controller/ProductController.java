package com.codigenics.ecommerce_app.controller;


import com.codigenics.ecommerce_app.config.AppConstants;
import com.codigenics.ecommerce_app.payload.ProductRequest;
import com.codigenics.ecommerce_app.payload.ProductResponse;
import com.codigenics.ecommerce_app.payload.ProductResponseList;
import com.codigenics.ecommerce_app.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ProductController {
    @Autowired
    private ProductService productService;

    @PostMapping("/admin/categories/{categoryId}/addProduct")
    public ResponseEntity<ProductResponse>  addProduct(@RequestBody ProductRequest productRequest , @PathVariable Long categoryId){
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
}
