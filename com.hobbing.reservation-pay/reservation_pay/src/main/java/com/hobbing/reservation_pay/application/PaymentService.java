package com.hobbing.reservation_pay.application;


import com.hobbing.reservation_pay.application.dto.CreatePaymentDto;
import com.hobbing.reservation_pay.application.dto.SearchPaymentsDto;
import com.hobbing.reservation_pay.application.dto.UpdatePaymentDto;
import com.hobbing.reservation_pay.common.exception.CommonErrorCode;
import com.hobbing.reservation_pay.common.exception.CustomException;
import com.hobbing.reservation_pay.domain.model.Payment;
import com.hobbing.reservation_pay.domain.model.Reservation;
import com.hobbing.reservation_pay.domain.model.status_enum.ReservationStatus;
import com.hobbing.reservation_pay.infrastructure.PaymentRepoInfra;
import com.hobbing.reservation_pay.infrastructure.ReservationRepoInfra;
import com.hobbing.reservation_pay.infrastructure.api.CouponFeignClient;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepoInfra paymentRepo;
    private final ReservationRepoInfra reservationRepo;

    private final CouponFeignClient couponServiceClient;


    public Payment readPayment(UUID paymentId) {
        return paymentRepo.readPayment(paymentId);
    }

    @Transactional
    public Payment payReservation(CreatePaymentDto dto) {

        Reservation reservation = reservationRepo.readReservation(dto.getReservationId());
        if (reservation.getStatus() == ReservationStatus.RESERVED_PAID) {
            throw new CustomException(CommonErrorCode.RESERVATION_ALREADY_PAYED);
        }

        Payment payment = paymentRepo.createPayment(dto);
        reservation.pay(payment);

        return payment;
    }

    @Transactional
    public void updatePayment(UUID paymentId, UpdatePaymentDto dto) {

        //todo : 서영님 api 완료하면  쿠폰 복원 api호출
//        PaymentStatus paymentStatus = dto.getPaymentStatus();
//        if (paymentStatus == PaymentStatus.REFUNDED
//        || paymentStatus.isTryingToRefund()) {
//        }

        paymentRepo.updatePayment(paymentId, dto);
    }

    public Page<Payment> searchPayments(SearchPaymentsDto dto) {

        return paymentRepo.searchPayments(dto);
    }
}
