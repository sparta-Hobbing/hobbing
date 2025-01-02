package com.hobbing.reservation_pay.domain.model;

public enum PaymentStatus {
    PAY_WAIT,
    PAYED,
    PAY_PG_API_ERROR,
    PG_DENIED_PAY,

    CANCEL_WAIT,
    CANCELED,
    CANCEL_PG_API_ERROR,
    PG_DENIED_CANCEL,

    OWNER_SETTLEMENT_WAIT,
    OWNER_SETTLED,
    PG_DENIED_OWNER_SETTLEMENT;
}
