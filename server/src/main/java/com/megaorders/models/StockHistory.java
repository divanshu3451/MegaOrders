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
@Table(name = "stock_history")
public class StockHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime receivedDate;
    private Long receivedQty;
    private Double price;

    @ManyToOne
    @JoinColumn(name = "product_supplier_id")
    private ProductSupplier productSupplier;

    @OneToOne(mappedBy = "stockDetail", cascade = CascadeType.ALL, orphanRemoval = true)
    private LiveStock liveStock;
}
