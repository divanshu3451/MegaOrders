package com.megaorders.models;

import com.megaorders.models.enums.PaymentMode;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
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
    @Column
    private Long id;

    @Enumerated(EnumType.STRING)
    private PaymentMode paymentMode;

    @Column
    private String cardNumber;

    @Column
    private String cardHolderName;

    @Column
    private String expirationMonth;

    @Column
    private String expirationYear;

    @Column
    private String upiId;

    @Column
    private String bankName;

    @Column
    private String bankAccountNumber;

    @Column
    private LocalDateTime createdAt;

    @Column
    private Boolean isSavedDetail;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "paymentDetail", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Order> orders = new ArrayList<>();
}
