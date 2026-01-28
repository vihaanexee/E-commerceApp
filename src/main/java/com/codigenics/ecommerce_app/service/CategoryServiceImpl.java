package com.codigenics.ecommerce_app.service;

import com.codigenics.ecommerce_app.entity.Category;
import com.codigenics.ecommerce_app.exceptions.APIException;
import com.codigenics.ecommerce_app.exceptions.ResourceNotFoundException;
import com.codigenics.ecommerce_app.payload.CategoryListResponse;
import com.codigenics.ecommerce_app.payload.CategoryRequest;
import com.codigenics.ecommerce_app.payload.CategoryResponse;
import com.codigenics.ecommerce_app.repositories.CategoryRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService{

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;
    private Category categories;


    //getting all the requested categories
    @Override
    public CategoryListResponse getAllCategories(Integer pageNumber , Integer pageSize , String sortBy , String sortOrder ) {

        //using sorting to sort the result by order of any parameter
        Sort sortByAndOrder  = sortOrder.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending():Sort.by(sortBy).descending();

        //using pageable to implement pagination as well as sorting using inbuilt methods
        Pageable pageDetails = PageRequest.of(pageNumber , pageSize ,sortByAndOrder);

        //finding all the categories that fall under the request
        Page<Category> categoryPage = categoryRepository.findAll(pageDetails);


        //a list of categories to set the page metadata and the content to turn it into a list
        List<Category> categories = categoryPage.getContent();
        if(categories.isEmpty()){
            throw new APIException("no categories present");
        }

        //turning that response into a list of categoryResponse dto using modelmapper
        List<CategoryResponse> categoryResponses = categories.stream().map(category -> modelMapper.map(category, CategoryResponse.class))
                .toList();
        CategoryListResponse categoryListResponse = new CategoryListResponse();

        //converting that response to the list response dto and addingt all the page metadata
        categoryListResponse.setContent(categoryResponses);
        categoryListResponse.setPageNumber(categoryPage.getNumber());
        categoryListResponse.setPageSize(categoryPage.getSize());
        categoryListResponse.setTotalElements(categoryPage.getTotalElements());
        categoryListResponse.setTotalPages(categoryPage.getTotalPages());
        categoryListResponse.setLastPage(categoryPage.isLast());
        return categoryListResponse;
    }


    //creating a category
    @Override
    public CategoryResponse createCategory(CategoryRequest categoryRequest) {
        //checking if the category already exists by its name and then throws an exception according to it
            Category existingCategory = categoryRepository.findByCategoryName(categoryRequest.getCategoryName());
            if(existingCategory != null){
                throw new APIException("category with that name exists buddy ");
            }
            //mapping the incoming request to category entity
            Category categoryToSave = modelMapper.map(categoryRequest , Category.class);

            //saving the mapped category to categoryResponse
            Category savedCategory = categoryRepository.save(categoryToSave);
            return modelMapper.map(savedCategory , CategoryResponse.class);

    }


    //deleting a category
    @Override
    public String deleteCategory(Long categoryId) {
        //finding the category by id to delete
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category" ,"categoryId" , categoryId));

        //deleting the cateogry
        categoryRepository.delete(category);
        return "category with categoryId: " + categoryId + " is deleted";
    }

    //deleting all the categories
    @Override
    @Transactional
    public String deleteAllCategories() {
        long count = categoryRepository.count();
        categoryRepository.deleteAll();
        return count + " categories deleted";
    }


    //updating the category using categoryId
    @Override
    public CategoryResponse updateCateogory(CategoryRequest categoryRequest, Long categoryId) {
        Category existingCategory = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("category" ,"categoryId" , categoryId));

        existingCategory.setCategoryName(categoryRequest.getCategoryName());
        existingCategory.setCategoryDescription(categoryRequest.getCategoryDescription());
        Category savedCategory = categoryRepository.save(existingCategory);
        return modelMapper.map(savedCategory , CategoryResponse.class);
    }

}
