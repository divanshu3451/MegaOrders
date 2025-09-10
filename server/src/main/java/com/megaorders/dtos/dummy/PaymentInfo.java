package com.megaorders.dtos.dummy;

import com.megaorders.models.enums.PaymentMode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentInfo {
    private PaymentMode paymentMode;
    private String upiId;
    private String cardNumber;
    private String bankAccountNumber;
}