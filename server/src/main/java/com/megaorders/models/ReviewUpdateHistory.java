package com.megaorders.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "review_update_history")
public class ReviewUpdateHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime createdAt;
    private Long rating;
    private String description;
    private Boolean wasAnonymous;

    @ManyToOne
    @JoinColumn(name = "review_id")
    private ProductReview productReview;

}
