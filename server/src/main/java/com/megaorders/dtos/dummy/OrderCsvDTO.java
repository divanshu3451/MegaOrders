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
public class OrderCsvDTO {
    private String email;
    private String cardNumber;
    private PaymentMode paymentMode;
    private String upiId;
    private String bankAccountNumber;
    private String transactionId;

    @CsvDate("yyyy-MM-dd")
    private LocalDate orderDate;

    @CsvDate("HH:mm:ss")
    private LocalTime orderTime;
}
