package com.codigenics.ecommerce_app.repositories;

import com.codigenics.ecommerce_app.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository <Category, Long> {
}
