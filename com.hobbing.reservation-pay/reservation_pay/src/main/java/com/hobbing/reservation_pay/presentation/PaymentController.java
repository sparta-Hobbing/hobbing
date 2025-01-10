package com.hobbing.reservation_pay.presentation;


import com.hobbing.reservation_pay.application.PaymentService;
import com.hobbing.reservation_pay.domain.model.Payment;
import com.hobbing.reservation_pay.presentation.dto.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
    public ApiResponse<GetPaymentResBody> getPayment(@PathVariable UUID id) {

        GetPaymentResBody resBody
                = GetPaymentResBody.from(paymentService.readPayment(id));

        return ApiResponse.ofSuccess(
                HttpStatus.OK, "OK", resBody
        );
    }

    @GetMapping("/student-view")
    public ApiResponse<PagedModel<StudentSearchedPaymentRes>> searchPayments(
            @Valid @ModelAttribute PageInfo pageInfo,
            @Valid @ModelAttribute SearchPaymentsReqParams params
    ) {
        Page<StudentSearchedPaymentRes> searched
                = paymentService.searchPayments(params.toDto(pageInfo))
                .map(StudentSearchedPaymentRes::from);

        PagedModel<StudentSearchedPaymentRes> resBody
                = new PagedModel<>(searched);

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