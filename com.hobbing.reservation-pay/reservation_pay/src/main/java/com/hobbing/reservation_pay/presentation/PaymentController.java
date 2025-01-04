package com.hobbing.reservation_pay.presentation;


import com.hobbing.reservation_pay.application.PaymentService;
import com.hobbing.reservation_pay.presentation.dto.GetPaymentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentController {


    private final PaymentService paymentService;

    @GetMapping("/{id}")
    public GetPaymentResponse getPayment(UUID id) {

        GetPaymentResponse data =
                GetPaymentResponse.from(paymentService.readPayment(id));

        return data;
    }

}
