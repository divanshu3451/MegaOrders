package com.megaorders.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "product_supplier")
public class ProductSupplier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double offeredPrice;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "productSupplier", orphanRemoval = true, fetch = FetchType.LAZY)
    private List<StockHistory> stocks = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "productSupplier", orphanRemoval = true, fetch = FetchType.LAZY)
    private List<ProductCostPriceHistory> productCostPrices = new ArrayList<>();
}
