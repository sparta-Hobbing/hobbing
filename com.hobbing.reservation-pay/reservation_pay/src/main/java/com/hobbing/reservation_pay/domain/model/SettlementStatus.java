package com.hobbing.reservation_pay.domain.model;

public enum SettlementStatus {

    SETTLEMENT_WAIT,
    SETTLED,
    SETTLEMENT_PG_API_ERROR,
    PG_DENIED_SETTLEMENT,
}
