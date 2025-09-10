package com.megaorders.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

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
    private LocalDate receivedDate;
    private LocalTime receivedTime;
    private Long receivedQty;
    private Double price;

    @ManyToOne
    @JoinColumn(name = "product_supplier_id")
    private ProductSupplier productSupplier;

    @OneToOne(mappedBy = "stockDetail", cascade = CascadeType.ALL, orphanRemoval = true)
    private LiveStock liveStock;

    public void addLiveStock(LiveStock liveStock) {
        liveStock.setStockDetail(this);
        this.liveStock = liveStock;
    }

    public void removeLiveStock(LiveStock liveStock) {
        liveStock.setStockDetail(null);
        this.liveStock = null;
    }
}
