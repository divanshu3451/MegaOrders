package com.megaorders.dtos.dummy;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductSupplierCsvDTO {
    private String productName;
    private String categoryName;
    private String licenseNumber;
    private Double costPrice;
}
