package com.hobbing.lecture.application.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class LectureScheduleRequestDto {
    private LocalDateTime startTime; // 시작 시간
    private LocalDateTime endTime;   // 종료 시간
}
