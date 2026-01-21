package com.codigenics.ecommerce_app.controller;

import com.codigenics.ecommerce_app.entity.Category;
import com.codigenics.ecommerce_app.service.CategoryServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/public/categories")
public class CategoryController {

    @Autowired
    private CategoryServiceImpl categoryService;

    @PostMapping("/createCategory")
    public ResponseEntity<Category> createCategory(@Valid @RequestBody Category category){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(categoryService.createCategory(category));
    }

    @GetMapping("/getCategories")
    public ResponseEntity<List<Category>> getAllCategories(){
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long categoryId) {
        return ResponseEntity.ok(categoryService.deleteCategory(categoryId));
    }

    @PutMapping("/{categoryId}")
    public ResponseEntity<String> updateCategory(@PathVariable Long categoryId, @RequestBody Category category){
        categoryService.updateCateogory(category, categoryId);
        return ResponseEntity.noContent().build();
    }
}