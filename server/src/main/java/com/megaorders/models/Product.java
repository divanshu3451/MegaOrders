package com.megaorders.models;

import com.megaorders.models.enums.ProductStatus;
import jakarta.persistence.*;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Double currentPrice;
    private Double sellingPrice;
    private Long reviewCount;
    private Double rating;
    private Long fiveStars;
    private Long fourStars;
    private Long threeStars;
    private Long twoStars;
    private Long oneStars;
    private String description;

    @Enumerated(EnumType.STRING)
    private ProductStatus status;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private ProductCategory category;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderProduct> orderedProducts = new ArrayList<>();

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductReview> reviews = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "product", orphanRemoval = true, fetch = FetchType.LAZY)
    private List<ProductSupplier> suppliers = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "product", orphanRemoval = true, fetch = FetchType.LAZY)
    private List<ProductSellingPriceHistory> productSellingPrices = new ArrayList<>();
}
