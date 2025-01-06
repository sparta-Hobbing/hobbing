package com.hobbing.reservation_pay.application;


import com.hobbing.reservation_pay.application.dto.CreatePaymentDto;
import com.hobbing.reservation_pay.application.dto.SearchPaymentsDto;
import com.hobbing.reservation_pay.application.dto.UpdatePaymentDto;
import com.hobbing.reservation_pay.domain.model.Payment;
import com.hobbing.reservation_pay.infrastructure.PaymentRepository;
import com.hobbing.reservation_pay.infrastructure.ReservationRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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

        //todo : 서영님 api 완료하면  쿠폰 복원 api호출
//        if (dto.getPaymentStatus().isTryingToRefund()) {
//        }

        paymentRepo.updatePayment(paymentId, dto);
    }

    public Page<Payment> searchPayments(SearchPaymentsDto dto) {

        return paymentRepo.searchPayments(dto);
    }
}
