package com.megaorders.repositories;

import com.megaorders.models.Item;
import com.megaorders.models.Product;
import com.megaorders.models.enums.DeliveryStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findByStatus(DeliveryStatus status );
}
