package com.megaorders.models;

import com.megaorders.models.embeddables.DeliveryInfo;
import com.megaorders.models.embeddables.ReturnInfo;
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

    @Embedded
    private DeliveryInfo deliveryInfo;

    @Embedded
    private ReturnInfo returnInfo;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Item)) return false;
        Item item = (Item) o;
        return serialNumber != null && serialNumber.equals(item.serialNumber);
    }

    @Override
    public int hashCode() {
        return serialNumber != null ? serialNumber.hashCode() : 0;
    }
}
