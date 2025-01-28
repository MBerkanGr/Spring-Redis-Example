package com.mehmetberkan.springrediscategorycacheexample.repository;

import com.mehmetberkan.springrediscategorycacheexample.model.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, UUID> {
}
