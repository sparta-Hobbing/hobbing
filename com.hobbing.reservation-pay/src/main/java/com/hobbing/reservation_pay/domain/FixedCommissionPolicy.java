package com.hobbing.reservation_pay.domain;


import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;


@Primary
@Service
public class FixedCommissionPolicy implements CommissionPolicy {

    public static final double COMMISSION_RATE = 0.1;


    @Override
    public long calculateCommission(long totalAmount) {
        return (long) ((double) totalAmount * COMMISSION_RATE);
    }
}
