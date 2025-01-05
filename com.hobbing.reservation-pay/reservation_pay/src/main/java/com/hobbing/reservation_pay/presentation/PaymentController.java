package com.hobbing.reservation_pay.presentation;


import com.hobbing.reservation_pay.application.PaymentService;
import com.hobbing.reservation_pay.domain.model.Payment;
import com.hobbing.reservation_pay.presentation.dto.GetPaymentResBody;
import com.hobbing.reservation_pay.presentation.dto.PostPaymentReqBody;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentController {


    private final PaymentService paymentService;

    @GetMapping("/{id}")
    public GetPaymentResBody getPayment(@PathVariable UUID id) {

        GetPaymentResBody data
                = GetPaymentResBody.from(paymentService.readPayment(id));

        return data;
    }

    @PostMapping
    public UUID postPayment(@RequestBody PostPaymentReqBody reqBody) {

        Payment payment = paymentService.payReservation(reqBody.toDto());

        return payment.getId();

    }

}
