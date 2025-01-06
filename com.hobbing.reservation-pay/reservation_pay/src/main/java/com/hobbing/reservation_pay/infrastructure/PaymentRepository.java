package com.hobbing.reservation_pay.infrastructure;

import com.hobbing.reservation_pay.application.dto.CreatePaymentDto;
import com.hobbing.reservation_pay.application.dto.SearchPaymentsDto;
import com.hobbing.reservation_pay.application.dto.UpdatePaymentDto;
import com.hobbing.reservation_pay.domain.model.Payment;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.UUID;


@Repository
@RequiredArgsConstructor
public class PaymentRepository {

    private final PaymentJpaRepository jpaRepo;


    public Payment readPayment(UUID paymentId) {
        return jpaRepo.findById(paymentId)
                .orElseThrow(() -> new RuntimeException("not exist"));//todo exception
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

    @Transactional
    public void updatePayment(UUID paymentId, UpdatePaymentDto dto) {

        Payment target = jpaRepo.findById(paymentId)
                .orElseThrow();//todo exception 처리

        target.updatePayInfo(
                dto.getReceipt(),
                dto.getPaymentStatus(),
                dto.getPayedPrice(),
                dto.getTransactionPgToken()
        );


    }

    public Page<Payment> searchPayments(SearchPaymentsDto dto) {

        Page<Payment> searched
                = jpaRepo.findByCreatedAtBetween(
                dto.getPayedAfter(),
                dto.getPayedBefore(),
                (Pageable) dto.getPageRequest()
        );

        if (searched.isEmpty()) {
            //todo exception
        }

        return searched;
    }
}
