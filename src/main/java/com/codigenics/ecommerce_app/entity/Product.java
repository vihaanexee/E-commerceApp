package com.codigenics.ecommerce_app.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DialectOverride;
import org.hibernate.annotations.Where;
import org.springframework.data.annotation.LastModifiedDate;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Products")
@Where(clause = "active = true")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @Column(nullable = false )
    @Min(0)
    private BigDecimal price;
    @Min(0)
    private BigDecimal specialPrice;
    @Min(0)
    @NotNull
    private BigDecimal discount;

    @ManyToOne
    @JoinColumn(name = "category_Id")
    private Category category;


    @Column(nullable = false)
    private Boolean active = true;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
