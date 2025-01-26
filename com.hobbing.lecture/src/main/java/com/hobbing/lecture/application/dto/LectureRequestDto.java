package com.hobbing.lecture.application.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class LectureRequestDto {
    private String title;
    private String description;
    private int price;
    private int maxParticipants;
    private String location;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
}
