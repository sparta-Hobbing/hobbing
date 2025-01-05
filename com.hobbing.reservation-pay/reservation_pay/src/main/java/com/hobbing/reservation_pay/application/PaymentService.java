package com.hobbing.reservation_pay.application;


import com.hobbing.reservation_pay.domain.model.Payment;
import com.hobbing.reservation_pay.infrastructure.PaymentRepository;
import com.hobbing.reservation_pay.infrastructure.ReservationRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepo;
    private final ReservationRepository reservationRepo;

    public Payment readPayment(UUID paymentId) {
        return paymentRepo.readPayment(paymentId);
    }

    @Transactional
    public Payment payReservation(CreatePaymentDto dto) {

        Payment payment = paymentRepo.createPayment(dto);
        reservationRepo.updatePayment(dto.getReservationId(), payment);

        return payment;
    }
}
