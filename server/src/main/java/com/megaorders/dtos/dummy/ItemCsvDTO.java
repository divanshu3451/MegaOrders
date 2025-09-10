package com.megaorders.dtos.dummy;

import com.megaorders.models.enums.DeliveryStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemCsvDTO {
    private String serialNumber;
    private String imeiNumber;
    private DeliveryStatus status;
    private String productName;
    private String categoryName;
    private String supplierLicenseNumber;
}
