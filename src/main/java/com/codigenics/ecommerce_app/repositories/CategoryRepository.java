package com.codigenics.ecommerce_app.repositories;

import com.codigenics.ecommerce_app.enitity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository <Category, Long> {
}
