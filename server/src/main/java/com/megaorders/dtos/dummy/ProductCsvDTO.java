

package com.megaorders.dtos.dummy;

import com.megaorders.models.enums.ProductStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductCsvDTO {
    private String name;
    private Double currentPrice;
    private Double sellingPrice;
    private String description;
    private ProductStatus status;
    private String categoryName;
    private Long reviewCount;
    private Double rating;
    private Long fiveStars;
    private Long fourStars;
    private Long threeStars;
    private Long twoStars;
    private Long oneStars;
}
