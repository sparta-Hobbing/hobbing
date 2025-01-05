package com.hobbing.reservation_pay.application;


import com.hobbing.reservation_pay.application.dto.CreatePaymentDto;
import com.hobbing.reservation_pay.application.dto.UpdatePaymentDto;
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

    private final CouponServiceClient couponServiceClient;


    public Payment readPayment(UUID paymentId) {
        return paymentRepo.readPayment(paymentId);
    }

    @Transactional
    public Payment payReservation(CreatePaymentDto dto) {

        Payment payment = paymentRepo.createPayment(dto);
        reservationRepo.updatePayment(dto.getReservationId(), payment);

        return payment;
    }

    @Transactional
    public void updatePayment(UUID paymentId, UpdatePaymentDto dto) {

        //todo : 결제 취소 시 쿠폰 복원
//        if (dto.getPaymentStatus().isTryingToRefund()) {
//        }

        paymentRepo.updatePayment(paymentId, dto);
    }
}
