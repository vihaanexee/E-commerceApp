package com.codigenics.ecommerce_app.repositories;

import com.codigenics.ecommerce_app.entity.Category;
import com.codigenics.ecommerce_app.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product , Long> {
    Page<Product> findByCategory(Category category, Pageable pageable);

    List<Product> findByProductNameContainingIgnoreCase(String keyword);
}
