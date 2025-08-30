package com.megaorders.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "product_cost_price_history")
public class ProductCostPriceHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double priceUpdateTo;
    private Double priceUpdateFrom;
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "product_supplier_id")
    private ProductSupplier productSupplier;


}
