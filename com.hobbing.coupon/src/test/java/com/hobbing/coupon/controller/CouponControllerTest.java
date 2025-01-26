package com.hobbing.coupon.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hobbing.coupon.dto.CreateCouponRequest;
import com.hobbing.coupon.dto.CouponResponse;
import com.hobbing.coupon.model.DiscountType;
import com.hobbing.coupon.service.CouponService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CouponController.class)
class CouponControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CouponService couponService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("쿠폰 생성 테스트")
    void createCoupon_success() throws Exception {
        // Given
        CreateCouponRequest request = new CreateCouponRequest(
                "Test Coupon",
                DiscountType.AMOUNT,
                BigDecimal.valueOf(10.0),
                BigDecimal.valueOf(5.0),
                BigDecimal.valueOf(50.0),
                LocalDateTime.now(),
                LocalDateTime.now().plusDays(5),
                LocalDateTime.now().plusDays(30),
                100
        );

        CouponResponse response = new CouponResponse(
                UUID.randomUUID().toString(),
                "Test Coupon",
                DiscountType.AMOUNT,
                BigDecimal.valueOf(10.0),
                BigDecimal.valueOf(5.0),
                BigDecimal.valueOf(50.0),
                LocalDateTime.now(),
                LocalDateTime.now().plusDays(5),
                LocalDateTime.now().plusDays(30),
                100,
                0
        );

        Mockito.when(couponService.createCoupon(Mockito.any())).thenReturn(response);

        // When & Then
        mockMvc.perform(post("/api/coupons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.couponName").value("Test Coupon"))
                .andExpect(jsonPath("$.data.discountAmount").value(10.0));
    }
}
