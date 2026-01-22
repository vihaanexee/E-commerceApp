package com.codigenics.ecommerce_app.service;

import com.codigenics.ecommerce_app.entity.Category;
import com.codigenics.ecommerce_app.exceptions.APIException;
import com.codigenics.ecommerce_app.exceptions.ResourceNotFoundException;
import com.codigenics.ecommerce_app.repositories.CategoryRepository;
import jakarta.transaction.Transactional;
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
        List<Category> categories = categoryRepository.findAll();
        if(categories.isEmpty()){
            throw new APIException("no categories present");
        }
        return categories;
    }

    @Override
    public Category createCategory(Category category) {
            Category savedCategory = categoryRepository.findByCategoryName(category.getCategoryName());
            if (savedCategory != null){
                throw new APIException("Category with that name already exists");
            }
            return categoryRepository.save(category);
    }

    @Override
    public String deleteCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("category" ,"categoryId" , categoryId));

        categoryRepository.delete(category);
        return "category with categoryId: " + categoryId + " is deleted";
    }

    @Override
    @Transactional
    public String deleteAllCategories() {
        long count = categoryRepository.count();
        categoryRepository.deleteAll();
        return count + " categories deleted";
    }

    @Override
    public Category updateCateogory(Category category, Long categoryId) {
        Category category1 = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("category" ,"categoryId" , categoryId));

        category1.setCategoryName(category.getCategoryName());
        Category saved = categoryRepository.save(category1);
        return saved;
    }

}
