package com.codigenics.ecommerce_app.service;

import com.codigenics.ecommerce_app.entity.Category;
import com.codigenics.ecommerce_app.entity.Product;
import com.codigenics.ecommerce_app.exceptions.APIException;
import com.codigenics.ecommerce_app.exceptions.ResourceNotFoundException;
import com.codigenics.ecommerce_app.payload.CategoryListResponse;
import com.codigenics.ecommerce_app.payload.ProductRequest;
import com.codigenics.ecommerce_app.payload.ProductResponse;
import com.codigenics.ecommerce_app.payload.ProductResponseList;
import com.codigenics.ecommerce_app.repositories.CategoryRepository;
import com.codigenics.ecommerce_app.repositories.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService{

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;
    @Override
    public ProductResponse addProduct(ProductRequest productRequest, Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category" , "categoryId" ,categoryId ));

        Product product = modelMapper.map(productRequest , Product.class);
        product.setImage("default.img");
        product.setCategory(category);

        BigDecimal discountPercent = product.getDiscount().divide(new BigDecimal(100) ,2 , RoundingMode.HALF_UP);
        BigDecimal discountAmount = product.getPrice().multiply(discountPercent);
        BigDecimal specialPrice = product.getPrice().subtract(discountAmount);


        product.setSpecialPrice(specialPrice);

        Product savedProduct = productRepository.save(product);
        return modelMapper.map(savedProduct , ProductResponse.class);
    }



    @Override
    public ProductResponseList getAllProducts(Integer pageNumber , Integer pageSize) {

        Pageable pageDetails = PageRequest.of(pageNumber , pageSize);
        Page<Product> productsPage = productRepository.findAll(pageDetails);

        List<Product> products = productsPage.getContent();
        if (products.isEmpty()){
            throw new APIException("no products found");
        }
        List<ProductResponse> productResponess = products.stream().map(product -> modelMapper.map(product , ProductResponse.class)).toList();

        ProductResponseList productResponseList = new ProductResponseList();
        productResponseList.setContent(productResponess);
        productResponseList.setPageNumber(productsPage.getNumber());
        productResponseList.setPageSize(productsPage.getSize());
        productResponseList.setTotalElements(productsPage.getTotalElements());
        productResponseList.setTotalPages(productsPage.getTotalPages());
        productResponseList.setLastPage(productsPage.isLast());

        return productResponseList;
    }


}
