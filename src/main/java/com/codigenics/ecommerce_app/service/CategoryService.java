package com.codigenics.ecommerce_app.service;

import com.codigenics.ecommerce_app.payload.CategoryListResponse;
import com.codigenics.ecommerce_app.payload.CategoryRequest;
import com.codigenics.ecommerce_app.payload.CategoryResponse;


//interface of the methods to implement
public interface CategoryService {
    CategoryListResponse getAllCategories(Integer pageNumber, Integer pageSize , String sortBy , String sortOrder);
    CategoryResponse createCategory(CategoryRequest categoryRequest);
    String deleteCategory(Long categoryId);
    String deleteAllCategories();
    CategoryResponse updateCateogory(CategoryRequest categoryRequest, Long categoryId);
}
