package com.mehmetberkan.springrediscategorycacheexample.controller;

import com.mehmetberkan.springrediscategorycacheexample.model.ProductCategory;
import com.mehmetberkan.springrediscategorycacheexample.service.ProductCategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class ProductCategoryController {

    private final ProductCategoryService productCategoryService;

    public ProductCategoryController(ProductCategoryService productCategoryService) {
        this.productCategoryService = productCategoryService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductCategory> getCategory(@PathVariable String id) {
        return ResponseEntity.ok(productCategoryService.getProductCategory(id));
    }

    @GetMapping
    public ResponseEntity<List<ProductCategory>> getAllProductCategories() {
        return ResponseEntity.ok(productCategoryService.getAllProductCategories());
    }
}
