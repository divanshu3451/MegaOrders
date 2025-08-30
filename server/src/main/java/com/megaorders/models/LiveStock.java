package com.megaorders.models;


import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "live_stock")
public class LiveStock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column
    private Long availableQty;

    @OneToOne
    @JoinColumn(name = "stock_detail_id", unique = true)
    private StockHistory stockDetail;
}
