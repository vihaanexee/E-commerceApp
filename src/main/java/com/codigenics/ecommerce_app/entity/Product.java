package com.codigenics.ecommerce_app.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long productId;

    @Column(nullable = false)
    @Size(min = 3 , max = 500)
    private String productName;

    @Column(nullable = false)
    @Size(min = 5, max = 500)
    private String description;

    @Column(nullable = false)
    private Integer quantity;

    @Column(length = 1000)
    private String image;

    @Column(nullable = false)
    private BigDecimal price;

    private BigDecimal specialPrice;

    private BigDecimal discount;

    @ManyToOne
    @JoinColumn(name = "category_Id")
    private Category category;


    @Column(nullable = false)
    private Boolean active = true;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;
}
