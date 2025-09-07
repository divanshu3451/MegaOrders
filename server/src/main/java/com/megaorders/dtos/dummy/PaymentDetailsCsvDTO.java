package com.megaorders.dtos.dummy;

import com.megaorders.models.enums.PaymentMode;
import com.opencsv.bean.CsvDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDetailsCsvDTO {
    private String email;
    private String cardNumber;
    private String cardHolderName;
    private Integer expirationMonth;
    private Integer expirationYear;
    private String upiId;
    private String bankName;
    private String bankAccountNumber;
    private PaymentMode paymentMode;
    private Boolean isSavedDetail;

    @CsvDate("yyyy-MM-dd")
    private LocalDate createdDate;
    @CsvDate("HH:mm:ss")
    private LocalTime createdTime;
}
