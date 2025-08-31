package com.megaorders.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "30_days_product_revenue")
public class Product30DaysRevenue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double revenue;
    private Long volumeSold;
    private Long volumeReturned;
    private Long volumeReturnedDueToSupplierFault;

    @OneToOne(mappedBy = "last30DaysRevenue", cascade = CascadeType.ALL, orphanRemoval = true)
    private Product product;
}
