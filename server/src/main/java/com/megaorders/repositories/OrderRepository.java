package com.megaorders.repositories;

import com.megaorders.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByOrderDate(LocalDate orderDate);
    Optional<Order> findByTransactionId(String transactionId);
}
