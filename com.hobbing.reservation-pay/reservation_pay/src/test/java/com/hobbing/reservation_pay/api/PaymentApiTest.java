package com.hobbing.reservation_pay.api;


import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@SpringBootTest
@AutoConfigureMockMvc
public class PaymentApiTest {

//    @MockitoBean
//    private PaymentRepository paymentRepository;
//
//    @Autowired
//    private MockMvc mockMvc;
//
//
//    @Test
//    void getPayment() throws Exception {
//        //given
//        Payment payment
//                = Payment.builder()
//                .id(UUID.randomUUID())
//                .receipt("receipt")
//                .status(PaymentStatus.PAYED)
//                .payedPrice(1233000)
//                .transactionPgToken("token")
//                .build();
//
//        given(paymentRepository.readPayment(any(UUID.class))).willReturn(payment);
//
//        mockMvc.perform(get("/payments/" + payment.getId()))
//                .andExpect(status().isOk())
////                .andExpect(jsonPath("$.couponId").value(payment.getCouponId()))
////                .andExpect(jsonPath("$.couponName").value(payment.getCouponName()))
//                .andExpect(jsonPath("$.receipt").value(payment.getReceipt()))
//                .andExpect(jsonPath("$.status").value(payment.getStatus()))
//                .andExpect(jsonPath("$.payedPrice").value(payment.getPayedPrice()))
//                .andExpect(jsonPath("$.transactionPgToken").value(payment.getTransactionPgToken()))
////                .andExpect(jsonPath("$.discountedPrice").value(payment.getDiscountedPrice()))
//                .andExpect(jsonPath("$.createdAt").value(payment.getCreatedAt()))
//                .andExpect(jsonPath("$.createdBy").value(payment.getCreatedBy()))
////                .andExpect(jsonPath("$.updatedAt").value(payment.getUpdatedAt()))
////                .andExpect(jsonPath("$.updatedBy").value(payment.getUpdatedBy()))
//                ;
//    }

}
