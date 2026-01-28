package com.codigenics.ecommerce_app.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


// a list response for all the categories present i'e returning all the categories rather than just one
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryListResponse {
    private List<CategoryResponse> content ;

    private Integer pageNumber;
    private Integer pageSize;
    private Long totalElements;
    private Integer totalPages ;
    private boolean lastPage;
}
