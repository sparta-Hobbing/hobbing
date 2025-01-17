package com.hobbing.lecture.application.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class StudentResponseDto {
    private UUID id;           // 학생 ID
    private String name;       // 학생 이름
    private String email;      // 학생 이메일
    private UUID lectureId;    // 강의 ID
    private Boolean isAttended; // 출석 여부
    private Boolean isCompleted; // 완료 여부
}
