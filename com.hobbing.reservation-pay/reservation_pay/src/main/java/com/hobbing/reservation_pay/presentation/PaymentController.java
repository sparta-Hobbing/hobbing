package com.hobbing.reservation_pay.presentation;


import com.hobbing.reservation_pay.application.PaymentService;
import com.hobbing.reservation_pay.domain.model.Payment;
import com.hobbing.reservation_pay.presentation.dto.GetPaymentResBody;
import com.hobbing.reservation_pay.presentation.dto.PostPaymentReqBody;
import com.hobbing.reservation_pay.presentation.dto.PutPaymentReqBody;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentController {


    private final PaymentService paymentService;

    @GetMapping("/{id}")
    public ApiResponse<GetPaymentResBody> getPayment(@PathVariable UUID id) {

        GetPaymentResBody resBody
                = GetPaymentResBody.from(paymentService.readPayment(id));

        return ApiResponse.ofSuccess(
                HttpStatus.OK, "OK", resBody
        );
    }

    @PostMapping
    public ApiResponse<UUID> postPayment(@RequestBody PostPaymentReqBody reqBody) {

        Payment payment = paymentService.payReservation(reqBody.toDto());

        return ApiResponse.ofSuccess(
                HttpStatus.CREATED, "CREATED", payment.getId()
        );
    }

    @PutMapping("/{id}")
    public ApiResponse<Void> putPayment(@PathVariable UUID id,
                                        @RequestBody PutPaymentReqBody reqBody) {

        paymentService.updatePayment(id, reqBody.toDto());

        return ApiResponse.ofSuccess(
                HttpStatus.OK, "OK", null
        );
    }
}
