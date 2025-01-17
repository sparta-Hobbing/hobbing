package com.hobbing.lecture.application.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TutorRequestDto {
    private String name;  // 강사 이름
    private String email; // 강사 이메일
    private String bio;   // 강사 소개
}
