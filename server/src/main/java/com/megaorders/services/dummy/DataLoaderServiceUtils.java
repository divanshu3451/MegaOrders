package com.megaorders.services.dummy;

import com.megaorders.dtos.dummy.PaymentInfo;
import com.megaorders.models.PaymentDetail;
import com.megaorders.models.enums.PaymentMode;
import com.megaorders.repositories.PaymentDetailRepository;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.core.io.ClassPathResource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Optional;

public abstract class DataLoaderServiceUtils {
    public static Optional<PaymentDetail> getPaymentDetailFromPaymentMode(PaymentInfo paymentInfo, Optional<PaymentDetail> existing, PaymentDetailRepository paymentDetailRepository) {
        if (paymentInfo.getPaymentMode() == PaymentMode.UPI) {
            existing = paymentDetailRepository.findByUpiId(paymentInfo.getUpiId());
        }
        if (paymentInfo.getPaymentMode() == PaymentMode.CREDIT_CARD || paymentInfo.getPaymentMode() == PaymentMode.DEBIT_CARD) {
            existing = paymentDetailRepository.findByCardNumber(paymentInfo.getCardNumber());
        }
        if (paymentInfo.getPaymentMode() == PaymentMode.NET_BANKING) {
            existing = paymentDetailRepository.findByBankAccountNumber(paymentInfo.getBankAccountNumber());
        }

        return existing;
    }

    public static <T> List<T> csvToBeanMapping(String path, Class<T> type) throws IOException {
        ClassPathResource resource = new ClassPathResource(path);
        BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream()));

        CsvToBean<T> csvToBean = new CsvToBeanBuilder<T>(reader)
                .withType(type)
                .withIgnoreLeadingWhiteSpace(true)
                .build();

        return csvToBean.parse();
    }

    public static void callLoader(String entity){

    }
}
