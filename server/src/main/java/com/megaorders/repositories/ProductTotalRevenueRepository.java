package com.megaorders.repositories;

import com.megaorders.models.Product;
import com.megaorders.models.ProductTotalRevenue;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductTotalRevenueRepository extends JpaRepository<Long, ProductTotalRevenue> {
    Optional<ProductTotalRevenue> findByProduct(Product product);
}
