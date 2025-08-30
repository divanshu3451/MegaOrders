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

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Supplier supplier;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Vendor vendor;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PaymentDetail> paymentDetails = new ArrayList<>();

}


