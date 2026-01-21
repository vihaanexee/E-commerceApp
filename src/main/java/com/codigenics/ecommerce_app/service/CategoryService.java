package com.codigenics.ecommerce_app.service;

import com.codigenics.ecommerce_app.entity.Category;
import java.util.List;

public interface CategoryService {
    List<Category> getAllCategories();
    Category createCategory(Category category);
    String deleteCategory(Long categoryId);

    Category updateCateogory(Category category, Long categoryId);
}
