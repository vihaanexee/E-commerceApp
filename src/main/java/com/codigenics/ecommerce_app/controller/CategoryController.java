package com.codigenics.ecommerce_app.controller;

import com.codigenics.ecommerce_app.config.AppConstants;
import com.codigenics.ecommerce_app.payload.CategoryListResponse;
import com.codigenics.ecommerce_app.payload.CategoryRequest;
import com.codigenics.ecommerce_app.payload.CategoryResponse;
import com.codigenics.ecommerce_app.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/public/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

//creating categories
    @PostMapping("/createCategory")
    public ResponseEntity<CategoryResponse> createCategory(@Valid @RequestBody CategoryRequest categoryRequest){
        CategoryResponse categoryResponse = categoryService.createCategory(categoryRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryResponse);
    }


//getting all the categories
    @GetMapping("/getCategories")
    public ResponseEntity<CategoryListResponse> getAllCategories(
            @RequestParam(name = "pageNumber" , defaultValue = AppConstants.PAGE_NUMBER , required = false) Integer pageNumber,
            @RequestParam(name = "pageSize" , defaultValue = AppConstants.PAGE_SIZE , required = false) Integer pageSize,
            @RequestParam(name = "sortBy" , defaultValue = AppConstants.SORT_CATEGORY_BY , required = false) String sortBy,
            @RequestParam(name = "sortOrder" ,defaultValue = AppConstants.SORT_ORDER , required = false)String sortOrder){
        CategoryListResponse categoryListResponse = categoryService.getAllCategories(pageNumber , pageSize , sortBy , sortOrder);
        return ResponseEntity.ok(categoryListResponse);
    }

//deleting categories using categoryId
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long categoryId) {
        return ResponseEntity.ok(categoryService.deleteCategory(categoryId));
    }


//deleting all the categories at once
    @DeleteMapping("/deleteAll")
    public ResponseEntity<String> deleteAllCategories(){
        return ResponseEntity.ok(categoryService.deleteAllCategories());
    }


//updating all the categories
    @PutMapping("/{categoryId}")
    public ResponseEntity<CategoryResponse> updateCategory(@PathVariable Long categoryId, @RequestBody CategoryRequest categoryRequest){
        CategoryResponse categoryResponse = categoryService.updateCateogory(categoryRequest,categoryId);
        return ResponseEntity.ok(categoryResponse);
    }
}