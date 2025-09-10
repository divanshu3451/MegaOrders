package com.megaorders.repositories;

import com.megaorders.models.Product;
import com.megaorders.models.ProductSupplier;
import com.megaorders.models.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductSupplierRepository extends JpaRepository<ProductSupplier, Long> {
    Optional<ProductSupplier> findByProductAndSupplier(Product product, Supplier supplier);
}
