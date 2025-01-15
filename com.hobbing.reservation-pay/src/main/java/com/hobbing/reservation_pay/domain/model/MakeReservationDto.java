package com.hobbing.reservation_pay.domain.model;


import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;
import java.util.UUID;


@Value
@Builder
public class MakeReservationDto {

    UUID userId;
    String userNickname;
    UUID lectureScheduleId;
    String lectureTitle;
    LocalDateTime lectureScheduleStart;
    LocalDateTime lectureScheduleEnd;
    UUID tutorId;
    String tutorNickname;
}