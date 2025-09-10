package com.megaorders.models;

import jakarta.persistence.*;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "vendor", indexes = {@Index(name = "idx_vendor_gst_number", columnList = "gst_number")})
public class Vendor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String companyName;
    private String companyAddress;

    @Column(unique = true, nullable = false)
    private String gstNumber;

    @OneToOne
    @JoinColumn(name = "user_id", unique = true)
    private User user;
}
