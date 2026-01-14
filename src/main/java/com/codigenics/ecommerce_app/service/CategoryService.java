package com.codigenics.ecommerce_app.service;

import com.codigenics.ecommerce_app.enitity.Category;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CategoryService {
    List<Category> getAllCategories();
    void createCategory(Category category);
    String deleteCategory(Long categoryId);

    Category updateCateogory(Category category, Long categoryId);
}
