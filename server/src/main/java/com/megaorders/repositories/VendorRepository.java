package com.megaorders.repositories;

import com.megaorders.models.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VendorRepository extends JpaRepository<Vendor, Long> {
    Optional<Vendor> findByGstNumber(String gstNumber);
}
