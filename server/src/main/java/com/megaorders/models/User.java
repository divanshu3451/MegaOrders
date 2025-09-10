package com.megaorders.models;

import com.megaorders.models.enums.Role;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user", indexes = {@Index(name = "idx_user_role", columnList = "role")})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String firstName;
    private String middleName;
    private String lastName;
    private LocalDate registeredDate = LocalDate.now();
    private Boolean isPresent = true;

    @Column(unique = true)
    @NotBlank
    private String username;

    @NotBlank
    @Column(unique = true)
    @Email
    private String email;

    @NotBlank
    @Size(min = 8, message = "Password must greater than 7 characters")
    private String password;

    @Enumerated(EnumType.STRING)
    @NotNull
    private Role role;


    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Order> orders = new ArrayList<>();

    public Boolean addOrder(Order order) {
        order.setUser(this);
        return this.orders.add(order);
    }

    public Boolean removeOrder(Order order) {
        order.setUser(null);
        return this.orders.remove(order);
    }

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Supplier supplier;

    public void addSupplier(Supplier supplier) {
        this.supplier = supplier;
        this.supplier.setUser(this);
    }

    public void removeSupplier(Supplier supplier) {
        this.supplier.setUser(null);
        this.supplier = null;
    }

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Vendor vendor;

    public void addVendor(Vendor vendor) {
        this.vendor = vendor;
        this.vendor.setUser(this);
    }

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PaymentDetail> paymentDetails = new ArrayList<>();

    public Boolean addPaymentDetail(PaymentDetail paymentDetail) {
        paymentDetail.setUser(this);
        return this.paymentDetails.add(paymentDetail);
    }

    public Boolean removePaymentDetail(PaymentDetail paymentDetail) {
        paymentDetail.setUser(null);
        return this.paymentDetails.remove(paymentDetail);
    }

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductReview> reviews = new ArrayList<>();

    public Boolean addProductReview(ProductReview productReview) {
        productReview.setUser(this);
        return this.reviews.add(productReview);
    }

    public Boolean removeProductReview(ProductReview productReview) {
        productReview.setUser(null);
        return this.reviews.remove(productReview);
    }

}


