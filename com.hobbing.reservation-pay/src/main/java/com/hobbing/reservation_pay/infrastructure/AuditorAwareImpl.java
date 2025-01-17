package com.hobbing.reservation_pay.infrastructure;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class AuditorAwareImpl implements AuditorAware<UUID> {

    @Override
    public Optional<UUID> getCurrentAuditor() {
        //todo jwt에서 userId 추출
        return Optional.of(UUID.fromString("7d8d248f-1584-4d0b-9d68-70787454e28b"));
    }

}