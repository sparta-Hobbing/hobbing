package com.hobbing.reservation_pay.domain.model.status_enum;

public enum PaymentStatus {
    PAY_WAIT,
    PAYED,
    PAY_PG_API_ERROR,
    PG_DENIED_PAY,

    REFUND_WAIT,
    REFUNDED,
    REFUND_PG_API_ERROR,
    PG_DENIED_REFUND;

    public boolean isTryingToPay() {
        return this == PaymentStatus.PAY_WAIT
                || this == PaymentStatus.PAY_PG_API_ERROR
                || this == PaymentStatus.PG_DENIED_PAY;
    }

    public boolean isTryingToRefund() {
        return this == PaymentStatus.REFUND_WAIT
                || this == PaymentStatus.REFUND_PG_API_ERROR
                || this == PaymentStatus.PG_DENIED_REFUND;
    }
}
