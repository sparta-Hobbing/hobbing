package com.hobbing.lecture.application.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class StudentRequestDto {
    private String name;  // 학생 이름
    private String email; // 학생 이메일
    private UUID lectureId; // 수강 중인 강의 ID
}
