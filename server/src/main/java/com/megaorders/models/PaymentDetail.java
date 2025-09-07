package com.megaorders.models;

import com.megaorders.models.enums.PaymentMode;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "payment_detail")
public class PaymentDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String cardNumber;
    private String cardHolderName;
    private String expirationMonth;
    private String expirationYear;
    private String upiId;
    private String bankName;
    private String bankAccountNumber;
    private LocalDate createdDate;
    private LocalTime createdTime;
    private Boolean isSavedDetail;

    @Enumerated(EnumType.STRING)
    private PaymentMode paymentMode;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "paymentDetail", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Order> orders = new ArrayList<>();

    public Boolean addOrder(Order order) {
        order.setPaymentDetail(this);
        return this.orders.add(order);
    }

    public Boolean removeOrder(Order order) {
        order.setPaymentDetail(null);
        return this.orders.remove(order);
    }
}
