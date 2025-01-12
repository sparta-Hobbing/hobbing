package com.hobbing.reservation_pay.presentation.dto;

import com.hobbing.reservation_pay.domain.model.MakeReservationDto;
import lombok.Value;

import java.time.LocalDateTime;
import java.util.UUID;

@Value
public class PostReservationReqBody {

    UUID userId;
    String userNickname;
    UUID lectureScheduleId;
    String lectureTitle;
    LocalDateTime lectureScheduleStart;
    LocalDateTime lectureScheduleEnd;
    UUID tutorId;
    String tutorNickname;


    public MakeReservationDto toDto() {

        return MakeReservationDto.builder()
                .userId(userId)
                .userNickname(userNickname)
                .lectureScheduleId(lectureScheduleId)
                .lectureTitle(lectureTitle)
                .lectureScheduleStart(lectureScheduleStart)
                .lectureScheduleEnd(lectureScheduleEnd)
                .tutorId(tutorId)
                .tutorNickname(tutorNickname)
                .build();
    }
}
