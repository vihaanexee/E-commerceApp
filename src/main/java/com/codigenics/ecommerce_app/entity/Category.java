package com.codigenics.ecommerce_app.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.security.PrivateKey;
import java.util.List;

@Entity
@Data
@Table(name = "categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId;

    @NotBlank
    @Size(min = 5 , message = "category name has to be atleast 5 characters")
    private String categoryName;

    @NotBlank
    @Size(min = 5 , message = "please describe your category")
    private String categoryDescription;

    @OneToMany(mappedBy = "category" , cascade = CascadeType.ALL)
    private List<Product> products;
}
