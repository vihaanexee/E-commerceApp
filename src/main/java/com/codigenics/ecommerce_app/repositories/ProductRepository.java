package com.codigenics.ecommerce_app.repositories;

import com.codigenics.ecommerce_app.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product , Long> {
}
