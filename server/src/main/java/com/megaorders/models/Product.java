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
@Table(name = "product", uniqueConstraints = @UniqueConstraint(columnNames = {"name", "category_id"}))
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
    private Long score;
    private Long scoreResetInDays;

    @Enumerated(EnumType.STRING)
    private ProductStatus status;

    @OneToOne(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private ProductTotalRevenue totalRevenue;

    public void addProductTotalRevenue(ProductTotalRevenue productTotalRevenue) {
        productTotalRevenue.setProduct(this);
        this.totalRevenue = productTotalRevenue;
    }

    public void removeProductTotalRevenue(ProductTotalRevenue productTotalRevenue) {
        productTotalRevenue.setProduct(null);
        this.totalRevenue = null;
    }

    @OneToOne(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private Product30DaysRevenue last30DaysRevenue;

    public void addProduct30DaysRevenue(Product30DaysRevenue product30DaysRevenue) {
        product30DaysRevenue.setProduct(this);
        this.last30DaysRevenue = product30DaysRevenue;
    }

    public void removeProduct30DaysRevenue(Product30DaysRevenue product30DaysRevenue) {
        product30DaysRevenue.setProduct(null);
        this.last30DaysRevenue = null;
    }

    @OneToOne(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private Product90DaysRevenue last90DaysRevenue;

    public void addProduct90DaysRevenue(Product90DaysRevenue product90DaysRevenue) {
        product90DaysRevenue.setProduct(this);
        this.last90DaysRevenue = product90DaysRevenue;
    }

    public void removeProduct90DaysRevenue(Product90DaysRevenue product90DaysRevenue) {
        product90DaysRevenue.setProduct(null);
        this.last90DaysRevenue = null;
    }

    @ManyToOne
    @JoinColumn(name = "category_id")
    private ProductCategory category;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderProduct> orderedProducts = new ArrayList<>();

    public Boolean addOrderProduct(OrderProduct orderProduct) {
        orderProduct.setProduct(this);
        return this.orderedProducts.add(orderProduct);
    }

    public Boolean removeOrderProduct(OrderProduct orderProduct) {
        orderProduct.setProduct(null);
        return this.orderedProducts.remove(orderProduct);
    }

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductReview> reviews = new ArrayList<>();

    public Boolean addProductReview(ProductReview productReview) {
        productReview.setProduct(this);
        return this.reviews.add(productReview);
    }

    public Boolean removeProductReview(ProductReview productReview) {
        productReview.setProduct(null);
        return this.reviews.remove(productReview);
    }

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "product", orphanRemoval = true, fetch = FetchType.LAZY)
    private List<ProductSupplier> suppliers = new ArrayList<>();

    public Boolean addProductSupplier(ProductSupplier productSupplier) {
        productSupplier.setProduct(this);
        return this.suppliers.add(productSupplier);
    }

    public Boolean removeProductSupplier(ProductSupplier productSupplier) {
        productSupplier.setProduct(null);
        return this.suppliers.remove(productSupplier);
    }

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "product", orphanRemoval = true, fetch = FetchType.LAZY)
    private List<ProductSellingPriceHistory> productSellingPrices = new ArrayList<>();

    public Boolean addProductSellingPriceHistory(ProductSellingPriceHistory productSellingPriceHistory) {
        productSellingPriceHistory.setProduct(this);
        return this.productSellingPrices.add(productSellingPriceHistory);
    }

    public Boolean removeProductSellingPriceHistory(ProductSellingPriceHistory productSellingPriceHistory) {
        productSellingPriceHistory.setProduct(null);
        return this.productSellingPrices.remove(productSellingPriceHistory);
    }

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "product", orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Item> items = new ArrayList<>();

    public Boolean addItem(Item item) {
        item.setProduct(this);
        return this.items.add(item);
    }

    public Boolean removeItem(Item item) {
        item.setProduct(null);
        return this.items.remove(item);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return this.id.equals(product.getId());
    }
}
