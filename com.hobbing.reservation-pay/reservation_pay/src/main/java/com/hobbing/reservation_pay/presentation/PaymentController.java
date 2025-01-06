package com.hobbing.reservation_pay.presentation;


import com.hobbing.reservation_pay.application.PaymentService;
import com.hobbing.reservation_pay.domain.model.Payment;
import com.hobbing.reservation_pay.presentation.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PagedModel;
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

    @GetMapping("/student-view")
    public PagedModel<StudentSearchedPaymentRes> searchPayments(@ModelAttribute PageInfo pageInfo,
                                                                @ModelAttribute SearchPaymentsReqParams params) {//todo : @Valid

        Page<StudentSearchedPaymentRes> searched
                = paymentService.searchPayments(params.toDto(pageInfo))
                .map(StudentSearchedPaymentRes::from);

        PagedModel<StudentSearchedPaymentRes> resBody
                = new PagedModel<>(searched);

        return resBody;
    }

    @PostMapping
    public UUID postPayment(@RequestBody PostPaymentReqBody reqBody) {

        Payment payment = paymentService.payReservation(reqBody.toDto());

        return payment.getId();
    }

    @PutMapping("/{id}")
    public void putPayment(@PathVariable UUID id,
                           @RequestBody PutPaymentReqBody reqBody) {

        paymentService.updatePayment(id, reqBody.toDto());

    }

}