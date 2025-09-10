package com.megaorders.repositories;

import com.megaorders.models.Product30DaysRevenue;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Product30DaysRevenueRepository extends JpaRepository<Product30DaysRevenue, Long> {
    
}
