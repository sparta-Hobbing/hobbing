package com.hobbing.lecture.application.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
public class LectureResponseDto {
    private UUID id;                 // 강의 ID
    private String title;            // 강의 제목
    private String description;      // 강의 설명
    private Integer price;           // 강의 가격
    private Integer maxParticipants; // 최대 참가자 수
    private String location;         // 강의 장소
    private LocalDateTime startDateTime; // 강의 시작 시간
    private LocalDateTime endDateTime;   // 강의 종료 시간
    private String status;           // 강의 상태
}
