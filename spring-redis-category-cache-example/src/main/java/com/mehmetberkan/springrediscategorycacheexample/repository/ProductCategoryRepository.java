package com.mehmetberkan.springrediscategorycacheexample.repository;

import com.mehmetberkan.springrediscategorycacheexample.model.ProductCategory;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ProductCategoryRepository {

    private final RedisTemplate<String, Object> redisTemplate;

    private static final String PRODUCT_CATEGORY_KEY = "productCategory:";

    public ProductCategoryRepository(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public ProductCategory getProductCategory(String id) {
        return (ProductCategory) redisTemplate.opsForValue().get(PRODUCT_CATEGORY_KEY + id);
    }

    public List<ProductCategory> getAllProductCategories() {
        List<ProductCategory> categories = new ArrayList<>();

        Cursor<byte[]> cursor = redisTemplate.getConnectionFactory()
                .getConnection()
                .scan(ScanOptions.scanOptions().match(PRODUCT_CATEGORY_KEY + "*").build());

        while (cursor.hasNext()) {
            byte[] key = cursor.next();
            String keyString = new String(key);
            ProductCategory category = (ProductCategory) redisTemplate
                    .opsForValue().get(keyString);

            categories.add(category);
        }
        return categories;
    }

    public void saveProductCategory(ProductCategory productCategory) {
        redisTemplate.opsForValue().set(PRODUCT_CATEGORY_KEY + productCategory.getId(), productCategory);
    }
}
