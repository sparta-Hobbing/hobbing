package com.hobbing.reservation_pay.application.dto;

import com.hobbing.reservation_pay.domain.model.status_enum.SettlementStatus;
import lombok.*;

import java.util.UUID;


@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class CreateSettlementDto {

    private final UUID tutorId;
    private final UUID lectureId;
    private final String lectureTitle;
    private final long totalAmount;
    @Setter
    private long commission;
    private SettlementStatus status;
    private String receipt;
    private final String transactionPgToken;

}
