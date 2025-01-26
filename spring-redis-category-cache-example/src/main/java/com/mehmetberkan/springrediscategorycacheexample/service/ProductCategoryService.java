package com.mehmetberkan.springrediscategorycacheexample.service;

import com.mehmetberkan.springrediscategorycacheexample.model.ProductCategory;
import com.mehmetberkan.springrediscategorycacheexample.repository.ProductCategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductCategoryService {

    private final ProductCategoryRepository productCategoryRepository;

    public ProductCategoryService(ProductCategoryRepository productCategoryRepository) {
        this.productCategoryRepository = productCategoryRepository;
    }

    public ProductCategory getProductCategory(String id) {
        ProductCategory category = productCategoryRepository.getProductCategory(id);

        if (category == null) {
            category = new ProductCategory(id, "Default Category");
            productCategoryRepository.saveProductCategory(category);
        }

        return category;
    }

    public List<ProductCategory> getAllProductCategories() {
        return productCategoryRepository.getAllProductCategories();
    }
}
