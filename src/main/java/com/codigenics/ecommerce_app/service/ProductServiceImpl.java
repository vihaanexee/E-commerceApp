package com.codigenics.ecommerce_app.service;

import com.codigenics.ecommerce_app.entity.Category;
import com.codigenics.ecommerce_app.entity.Product;
import com.codigenics.ecommerce_app.exceptions.APIException;
import com.codigenics.ecommerce_app.exceptions.ResourceNotFoundException;
import com.codigenics.ecommerce_app.payload.ProductRequest;
import com.codigenics.ecommerce_app.payload.ProductResponse;
import com.codigenics.ecommerce_app.payload.ProductResponseList;
import com.codigenics.ecommerce_app.repositories.CategoryRepository;
import com.codigenics.ecommerce_app.repositories.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService{

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private FileService fileService;

    @Value("${project.image}")
    private String path;


    @Override
    public ProductResponse addProduct(ProductRequest productRequest, Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category" , "categoryId" ,categoryId ));

        boolean isProductNotPresent = true;
        List<Product> products = category.getProducts();
        for (Product value : products) {
            if (value.getProductName().equals(productRequest.getProductName())){
                isProductNotPresent = false;
                break;
            }
        }
        if (isProductNotPresent) {
            Product product = modelMapper.map(productRequest, Product.class);
            product.setImage("default.img");
            product.setCategory(category);

            BigDecimal discountPercent = product.getDiscount().divide(new BigDecimal(100), 2, RoundingMode.HALF_UP);
            BigDecimal discountAmount = product.getPrice().multiply(discountPercent);
            BigDecimal specialPrice = product.getPrice().subtract(discountAmount);


            product.setSpecialPrice(specialPrice);

            Product savedProduct = productRepository.save(product);
            return modelMapper.map(savedProduct, ProductResponse.class);
        }else {
            throw new APIException("Product is already present");
        }
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

    @Override
    public ProductResponseList getByCategoryId(Long categoryId, Integer pageNumber, Integer pageSize) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));

        Pageable pageDetails = PageRequest.of(pageNumber,pageSize);

        Page<Product> productsPage = productRepository.findByCategory(category , pageDetails);


        List<ProductResponse> productResponses = productsPage.stream().map(product -> modelMapper.map(product , ProductResponse.class)).toList();

        ProductResponseList productResponseList = new ProductResponseList();
        productResponseList.setContent(productResponses);
        productResponseList.setPageNumber(productsPage.getNumber());
        productResponseList.setPageSize(productsPage.getSize());
        productResponseList.setTotalElements(productsPage.getTotalElements());
        productResponseList.setTotalPages(productsPage.getTotalPages());
        productResponseList.setLastPage(productsPage.isLast());

        return productResponseList;

    }

    @Override
    public ProductResponseList getProductByKeyword(String keyword) {
        List<Product> products = productRepository.findByProductNameContainingIgnoreCase(keyword);
        List<ProductResponse> productResponses = products.stream().map(product -> modelMapper.map( product , ProductResponse.class)).toList();
        ProductResponseList productResponseList = new ProductResponseList();
        productResponseList.setContent(productResponses);

        return productResponseList;
    }

    @Override
    public ProductResponse updateProduct(Long productId , ProductRequest productRequest) {
        Product existingProduct = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product" , "productId" , productId));
        existingProduct.setProductName(productRequest.getProductName());
        existingProduct.setQuantity(productRequest.getQuantity());
        existingProduct.setPrice(productRequest.getPrice());
        existingProduct.setDescription(productRequest.getDescription());
        existingProduct.setDiscount(productRequest.getDiscount());

        BigDecimal discountPercent = existingProduct.getDiscount().divide(new BigDecimal(100) ,2 , RoundingMode.HALF_UP);
        BigDecimal discountAmount = existingProduct.getPrice().multiply(discountPercent);
        BigDecimal specialPrice = existingProduct.getPrice().subtract(discountAmount);


        existingProduct.setSpecialPrice(specialPrice);
        existingProduct.setUpdatedAt(LocalDateTime.now());

        Product updatedProduct = productRepository.save(existingProduct);
        return modelMapper.map(updatedProduct , ProductResponse.class);

    }

    @Override
    public ProductResponse deletedProductById(Long productId) {
        Product productToBeDeleted = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product" ,"productId" , productId));
        productToBeDeleted.setActive(false);
        productToBeDeleted.setUpdatedAt(LocalDateTime.now());

        productToBeDeleted = productRepository.save(productToBeDeleted);

        ProductResponse productResponse = modelMapper.map(productToBeDeleted ,ProductResponse.class);
        productResponse.setMessage("This product is no longer Active");

        return  productResponse ;
    }

    @Override
    public ProductResponse updateProductImage(Long productId, MultipartFile image) throws IOException {
        //get prod from db
        Product existingProduct = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product" , "productId" , productId));

        // upload image to server i.e /image directory
        //get the file name of uploaded image
        String fileName = fileService.uploadImage(path , image);
        //updating the new file name to the product
        existingProduct.setImage(fileName);
        //save product again

        Product updatedProduct = productRepository.save(existingProduct);
        //return productResponse

        return modelMapper.map(updatedProduct , ProductResponse.class);
    }




}
