package com.mehmetberkan.springrediscategorycacheexample.controller;

import com.mehmetberkan.springrediscategorycacheexample.model.ProductCategory;
import com.mehmetberkan.springrediscategorycacheexample.service.ProductCategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/categories")
public class ProductCategoryController {

    private final ProductCategoryService productCategoryService;

    public ProductCategoryController(ProductCategoryService productCategoryService) {
        this.productCategoryService = productCategoryService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductCategory> getProductCategory(@PathVariable UUID id) {
        return ResponseEntity.ok(productCategoryService.getProductCategory(id));
    }

    @GetMapping
    public ResponseEntity<List<ProductCategory>> getAllProductCategories() {
        return ResponseEntity.ok(productCategoryService.getAllProductCategories());
    }

    @PostMapping
    public ResponseEntity<ProductCategory> saveProductCategory(@RequestBody ProductCategory productCategory) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(productCategoryService.saveProductCategory(productCategory));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductCategory> updateProductCategory(@PathVariable UUID id,
                                                                 @RequestBody ProductCategory productCategory) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(productCategoryService.updateProductCategory(id, productCategory));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProductCategory(@PathVariable UUID id) {
        productCategoryService.deleteProductCategory(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteAllProductCategory() {
        productCategoryService.deleteAllProductCategories();
        return ResponseEntity.noContent().build();
    }

}
