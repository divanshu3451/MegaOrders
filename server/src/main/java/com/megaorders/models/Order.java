package com.megaorders.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter

@Entity
@Table(name = "`order`")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate orderDate;
    private LocalTime orderTime;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderProduct> orderedProducts = new ArrayList<>();

    public Boolean addOrderProduct(OrderProduct orderProduct) {
        orderProduct.setOrder(this);
        return this.orderedProducts.add(orderProduct);
    }

    public Boolean removeOrderProduct(OrderProduct orderProduct) {
        orderProduct.setOrder(null);
        return this.orderedProducts.remove(orderProduct);
    }

    @ManyToOne
    @JoinColumn(name = "payment_detail_id")
    private PaymentDetail paymentDetail;

    @Column(unique = true)
    private String transactionId;
}
