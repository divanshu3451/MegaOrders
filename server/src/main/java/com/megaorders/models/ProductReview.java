package com.megaorders.models;

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
@Table(name = "product_review")
public class ProductReview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long rating;
    private String description;
    private Boolean isAnonymous;
    private LocalDate createdDate;
    private LocalTime createdTime;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "productReview", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReviewUpdateHistory> updateHistory = new ArrayList<>();

    public Boolean addReviewUpdateHistory(ReviewUpdateHistory reviewUpdateHistory) {
        reviewUpdateHistory.setProductReview(this);
        return this.updateHistory.add(reviewUpdateHistory);
    }

    public Boolean removeReviewUpdateHistory(ReviewUpdateHistory reviewUpdateHistory) {
        reviewUpdateHistory.setProductReview(null);
        return this.updateHistory.remove(reviewUpdateHistory);
    }
}
