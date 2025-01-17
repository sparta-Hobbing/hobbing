package com.hobbing.reservation_pay.domain.model.status_enum;

public enum ReservationStatus {
    RESERVED_UNPAID,
    RESERVED_PAID,
    CANCELED,
    UNPAID_CANCELED,
    ;


    public boolean isReserved() {
        return this == RESERVED_UNPAID || this == RESERVED_PAID;
    }
}
