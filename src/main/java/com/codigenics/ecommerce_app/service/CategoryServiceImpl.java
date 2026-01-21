package com.codigenics.ecommerce_app.service;

import com.codigenics.ecommerce_app.entity.Category;
import com.codigenics.ecommerce_app.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService{

    @Autowired
    private CategoryRepository categoryRepository;
    private Category categories;


    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category createCategory(Category category) {
            return categoryRepository.save(category);
    }

    @Override
    public String deleteCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        categoryRepository.delete(category);
        return "category with categoryId: " + categoryId + " is deleted";
    }

    @Override
    public Category updateCateogory(Category category, Long categoryId) {
        Category category1 = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("category not found"));

        category1.setCategoryName(category.getCategoryName());
        Category saved = categoryRepository.save(category1);
        return saved;
    }

}
