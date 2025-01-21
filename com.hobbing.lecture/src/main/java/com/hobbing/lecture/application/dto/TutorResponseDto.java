package com.hobbing.lecture.application.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class TutorResponseDto {
    private UUID id;      // 강사 ID
    private String name;  // 강사 이름
    private String email; // 강사 이메일
    private String bio;   // 강사 소개
    private double rating; // 강사 평점
    private int reviewCount; // 리뷰 수
}
