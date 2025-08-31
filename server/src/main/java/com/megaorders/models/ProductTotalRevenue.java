package com.megaorders.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table
public class ProductTotalRevenue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double revenue;
    private Long volumeSold;
    private Long volumeReturned;
    private Long volumeReturnedDueToSupplierFault;

    @OneToOne(mappedBy = "totalRevenue", cascade = CascadeType.ALL, orphanRemoval = true)
    private Product product;
}
