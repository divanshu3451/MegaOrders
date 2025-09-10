package com.megaorders.dtos.dummy;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VendorCsvDTO {
    private String email;
    private String companyName;
    private String companyAddress;
    private String gstNumber;
}
