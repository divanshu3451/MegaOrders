package com.megaorders.models;

import com.megaorders.models.enums.DeliveryStatus;
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
@Table(name = "item")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String serialNumber;
    private DeliveryStatus status;
    private LocalDateTime dispatchDate;
    private LocalDateTime nearestHubDate;
    private LocalDateTime outForDeliveryDate;
    private LocalDateTime deliveryDate;
    private LocalDateTime returnExpireDate;
    private LocalDateTime returnClaimDate;
    private Boolean isReturnAccepted;
    private LocalDateTime returnAcceptedDate;
    private LocalDateTime returnPickUpDate;
    private LocalDateTime refundDate;

    @ManyToOne
    @JoinColumn(name = "product_supplier_id")
    private ProductSupplier productSupplier;

    @ManyToOne
    @JoinColumn(name = "order_product_id")
    private OrderProduct orderProduct;
}
