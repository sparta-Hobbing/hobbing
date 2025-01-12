package com.hobbing.user.application.dto.response;

import lombok.Getter;

@Getter
public class VerifyResponse {
    private boolean isVerified;

    public VerifyResponse(boolean isVerified) {
        this.isVerified = isVerified;
    }
}