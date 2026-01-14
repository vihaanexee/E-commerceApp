package com.codigenics.ecommerce_app.service;

import com.codigenics.ecommerce_app.enitity.Category;
import com.codigenics.ecommerce_app.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService{

    @Autowired
    private CategoryRepository categoryRepository;
    private Category categories;


    @Override // getting all categories irrespective of id
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public void createCategory(Category category) {
            categoryRepository.save(category);
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
