package com.codigenics.ecommerce_app.controller;

import com.codigenics.ecommerce_app.enitity.Category;
import com.codigenics.ecommerce_app.service.CategoryService;
import com.codigenics.ecommerce_app.service.CategoryServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/public/categories")
@AllArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;


    @PostMapping("createCategory")
    public ResponseEntity<String> createCategory(@RequestBody Category category){
        categoryService.createCategory(category);
        return  new ResponseEntity<>("category added" , HttpStatus.CREATED);
    }

    @GetMapping("getCategories")
    public ResponseEntity<List<Category>> getAllCategories(){
        List<Category> categories = categoryService.getAllCategories();
        return new ResponseEntity<>(categories , HttpStatus.OK);
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long categoryId ) {
        try {
            String status = categoryService.deleteCategory(categoryId);
            return new ResponseEntity<>(status, HttpStatus.OK);
        } catch (ResponseStatusException e) {
            return new ResponseEntity<>(e.getReason(), e.getStatusCode());
        }
    }

    @PutMapping("/{categoryId}")
    public ResponseEntity<String> updateCategory(@PathVariable  Long categoryId , @RequestBody Category category ){
            try {
                Category savedCategory = categoryService.updateCateogory(category , categoryId);
                return new ResponseEntity<>("category with category id: " + category + "is updated" , HttpStatus.OK);
            } catch (ResponseStatusException e) {
                return new ResponseEntity<>(e.getReason() , e.getStatusCode());
            }
        }


}
