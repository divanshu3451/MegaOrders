package com.megaorders.models;

import com.megaorders.models.enums.SupplierType;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "supplier", indexes = {@Index(name = "idx_supplier_lincense_number", columnList = "license_number")})
public class Supplier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String licenseNumber;

    @Enumerated(EnumType.STRING)
    private SupplierType supplierType;

    @OneToOne
    @JoinColumn(name = "user_id", unique = true)
    private User user;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "supplier", orphanRemoval = true, fetch = FetchType.LAZY)
    private List<ProductSupplier> products = new ArrayList<>();
}
