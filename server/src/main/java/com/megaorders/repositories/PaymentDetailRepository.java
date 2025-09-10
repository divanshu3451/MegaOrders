package com.megaorders.repositories;

import com.megaorders.models.PaymentDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentDetailRepository extends JpaRepository<PaymentDetail, Long> {
    Optional<PaymentDetail> findByUpiId(String upiId);

    Optional<PaymentDetail> findByBankAccountNumber(String bankAccountNumber);

    Optional<PaymentDetail> findByCardNumber(String cardNumber);
}
