package com.hobbing.lecture.application.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LectureRequestDto {
    private String title;            // 강의 제목
    private String description;      // 강의 설명
    private Integer price;           // 강의 가격
    private Integer maxParticipants; // 최대 참가자 수
    private String location;         // 강의 장소
}
