package com.hobbing.reservation_pay.infrastructure;

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
}
