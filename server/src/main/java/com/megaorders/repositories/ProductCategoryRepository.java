package com.megaorders.repositories;

import com.megaorders.models.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long> {
    Optional<ProductCategory> findByName(String name);
}
