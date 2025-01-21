package com.hobbing.reservation_pay.domain;

public interface CommissionPolicy {

    long calculateCommission(long totalAmount);
}
