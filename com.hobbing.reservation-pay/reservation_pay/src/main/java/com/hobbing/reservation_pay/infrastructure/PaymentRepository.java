package com.hobbing.reservation_pay.infrastructure;

import com.hobbing.reservation_pay.application.CreatePaymentDto;
import com.hobbing.reservation_pay.domain.model.Payment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.UUID;


@Repository
@RequiredArgsConstructor
public class PaymentRepository {

    private final PaymentJpaRepository jpaRepo;


    public Payment readPayment(UUID paymentId) {
        return jpaRepo.findById(paymentId)
                .orElseThrow();//todo exception
    }

    public Payment createPayment(CreatePaymentDto createPaymentDto) {
        Payment payment
                = Payment.builder()
                .userId(createPaymentDto.getUserId())
                .couponId(createPaymentDto.getCouponId())
                .couponName(createPaymentDto.getCouponName())
                .receipt(createPaymentDto.getReceipt())
                .status(createPaymentDto.getPaymentStatus())
                .payedPrice(createPaymentDto.getPayedPrice())
                .transactionPgToken(createPaymentDto.getTransactionPgToken())
                .discountedPrice(createPaymentDto.getDiscountedPrice())
                .build();

        return jpaRepo.save(payment);
    }
}
