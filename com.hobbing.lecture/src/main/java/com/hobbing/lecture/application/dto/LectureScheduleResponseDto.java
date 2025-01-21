package com.hobbing.lecture.application.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class LectureScheduleResponseDto {
    private UUID id;             // 일정 ID
    private UUID lectureId;      // 강의 ID
    private LocalDateTime startTime; // 시작 시간
    private LocalDateTime endTime;   // 종료 시간
    private String status;       // 일정 상태
}
