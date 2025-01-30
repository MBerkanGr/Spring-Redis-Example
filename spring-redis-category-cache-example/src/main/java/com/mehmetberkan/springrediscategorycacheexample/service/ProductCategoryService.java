package com.mehmetberkan.springrediscategorycacheexample.service;

import com.mehmetberkan.springrediscategorycacheexample.model.ProductCategory;
import com.mehmetberkan.springrediscategorycacheexample.repository.ProductCategoryRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ProductCategoryService {

    private final RedisTemplate<String, ProductCategory> redisTemplate;
    private final ProductCategoryRepository productCategoryRepository;

    private static final String PRODUCT_CATEGORIES_KEY_PREFIX = "productCategories::";

    public ProductCategoryService(RedisTemplate<String, ProductCategory> redisTemplate, ProductCategoryRepository productCategoryRepository) {
        this.redisTemplate = redisTemplate;
        this.productCategoryRepository = productCategoryRepository;
    }

    @Cacheable(value = "productCategories", key = "#id")
    public ProductCategory getProductCategory(UUID id) {
        return productCategoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product category not found, ID : " + id));
    }

    public List<ProductCategory> getAllProductCategories() {
        return fetchAllProductCategoriesFromCache();
    }

    @CachePut(value = "productCategories", key = "#productCategory.id")
    public ProductCategory saveProductCategory(ProductCategory productCategory) {
        return productCategoryRepository.save(productCategory);
    }

    @CachePut(value = "productCategories", key = "#id")
    public ProductCategory updateProductCategory(UUID id, ProductCategory productCategoryToUpdate) {
        ProductCategory oldProductCategory = getProductCategory(id);

        oldProductCategory.setName(productCategoryToUpdate.getName());

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

    private List<ProductCategory> fetchAllProductCategoriesFromCache() {
        List<ProductCategory> categories = new ArrayList<>();
        Cursor<byte[]> cursor = redisTemplate.getConnectionFactory()
                .getConnection()
                .scan(ScanOptions.scanOptions().match(PRODUCT_CATEGORIES_KEY_PREFIX + "*").build());
        while (cursor.hasNext()) {
            byte[] key = cursor.next();
            String keyString = new String(key);
            ProductCategory category = (ProductCategory) redisTemplate
                    .opsForValue().get(keyString);
            categories.add(category);
        }

        if (categories.isEmpty()) {
            categories = productCategoryRepository.findAll();
            for (ProductCategory category : categories) {
                redisTemplate.opsForValue().set(PRODUCT_CATEGORIES_KEY_PREFIX + category.getId(), category);
            }
        }

        return categories;
    }
}
