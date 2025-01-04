package com.hobbing.reservation_pay.application;


import com.hobbing.reservation_pay.domain.model.Payment;
import com.hobbing.reservation_pay.infrastructure.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;


    public Payment readPayment(UUID paymentId) {
        return paymentRepository.readPayment(paymentId);
    }

}
