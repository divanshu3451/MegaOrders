package com.megaorders.models;

import com.megaorders.models.enums.DeliveryStatus;
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
@Table(name = "item", indexes = {@Index(name = "idx_item_status", columnList = "status")})
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String serialNumber;

    @Enumerated(EnumType.STRING)
    private DeliveryStatus status;
    private LocalDate dispatchDate;
    private LocalTime dispatchTime;
    private LocalDate nearestHubDate;
    private LocalTime nearestHubTime;
    private LocalDate outForDeliveryDate;
    private LocalTime outForDeliveryTime;
    private LocalDate deliveryDate;
    private LocalTime deliveryTime;
    private LocalDate returnExpireDate;
    private LocalTime returnExpireTime;
    private LocalDate returnClaimDate;
    private LocalTime returnClaimTime;
    private Boolean isReturnAccepted;
    private LocalDate returnAcceptedDate;
    private LocalTime returnAcceptedTime;
    private LocalDate returnPickUpDate;
    private LocalTime returnPickUpTime;
    private LocalDate refundDate;
    private LocalTime refundTime;

    @Column(unique = true)
    private String imeiNumber;

    @ManyToOne
    @JoinColumn(name = "product_supplier_id")
    private ProductSupplier productSupplier;

    @ManyToOne
    @JoinColumn(name = "order_product_id")
    private OrderProduct orderProduct;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
}
