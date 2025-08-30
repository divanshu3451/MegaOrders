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
    @Column
    private Long id;

    @Column
    private LocalDateTime receivedDate;

    @Column
    private Long receivedQty;

    @Column
    private Double price;

    @ManyToOne
    @JoinColumn(name = "product_id", unique = true)
    private Product product;

    @ManyToOne
    @JoinColumn(name = "supplier_id", unique = true)
    private Supplier supplier;

    @OneToOne(mappedBy = "stockDetail", cascade = CascadeType.ALL, orphanRemoval = true)
    private LiveStock liveStock;
}
