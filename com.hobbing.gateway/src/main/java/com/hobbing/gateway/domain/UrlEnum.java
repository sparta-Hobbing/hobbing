package com.hobbing.gateway.domain;

import lombok.Getter;

@Getter
public enum UrlEnum {
    PREFIX_USERS("/users"),

    PREFIX_AUTH("/auth"),

    PREFIX_LECTURES("/lectures"),

    PREFIX_WAITINGLIST("/waitinglist"),

    PREFIX_COUPONS("/coupons"),

    PREFIX_COUPONS_USER("/coupons"),

    PREFIX_RESERVATIONS("/reservations"),

    PREFIX_PAYMENTS("/payments"),

    PREFIX_SETTLEMENTS("/settlements"),
    ;

    private final String url;
    UrlEnum(String url) {
        this.url = url;
    }
}
