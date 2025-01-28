package com.mehmetberkan.springrediscategorycacheexample.service;

import com.mehmetberkan.springrediscategorycacheexample.model.ProductCategory;
import com.mehmetberkan.springrediscategorycacheexample.repository.ProductCategoryRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProductCategoryService {

    private final ProductCategoryRepository productCategoryRepository;

    public ProductCategoryService(ProductCategoryRepository productCategoryRepository) {
        this.productCategoryRepository = productCategoryRepository;
    }

    @Cacheable(value = "productCategories", key = "#id")
    public ProductCategory getProductCategory(UUID id) {
        return productCategoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product category not found, ID : " + id));
    }

    public List<ProductCategory> getAllProductCategories() {
        return productCategoryRepository.findAll();
    }

    @CachePut(value = "productCategories", key = "#productCategory.id")
    public ProductCategory saveProductCategory(ProductCategory productCategory) {
        return productCategoryRepository.save(productCategory);
    }

    @CachePut(value = "productCategories", key = "#productCategory.id")
    public ProductCategory updateProductCategory(UUID id, ProductCategory productCategory) {
        ProductCategory oldProductCategory = getProductCategory(id);

        oldProductCategory.setId(productCategory.getId());
        oldProductCategory.setName(productCategory.getName());

        return productCategoryRepository.save(oldProductCategory);
    }

    @CacheEvict(value = "productCategories", key = "#id", beforeInvocation = true)
    public void deleteProductCategory(UUID id) {
        productCategoryRepository.deleteById(id);
    }

    @CacheEvict(value = "productCategories", allEntries = true)
    public void deleteAllProductCategories() {
        productCategoryRepository.deleteAll();
    }
}
