package com.megaorders.dtos.dummy;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderProductCsvDTO {
    private Long qty;
    private Double price;
    private String productName;
    private String categoryName;
    private String transactionId;
    private String serialNumbers;
}
