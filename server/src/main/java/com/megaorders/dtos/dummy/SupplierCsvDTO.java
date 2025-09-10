package com.megaorders.dtos.dummy;

import com.megaorders.models.enums.SupplierType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SupplierCsvDTO {
    private String email;
    private String licenseNumber;
    private SupplierType supplierType;
}
