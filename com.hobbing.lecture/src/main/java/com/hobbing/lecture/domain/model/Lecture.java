package com.hobbing.lecture.domain.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
public class Lecture {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    private String title; // 강의 제목

    @Column(columnDefinition = "TEXT")
    private String description; // 강의 설명

    @Column(nullable = false)
    private int price; // 강의 가격

    @Column(nullable = false)
    private int maxParticipants; // 최대 수강생 수

    @Column(nullable = false)
    private String status = "AVAILABLE"; // 강의 상태

    @Column(nullable = false)
    private String location; // 강의 장소

    @Column(nullable = false)
    private boolean isDeleted = false; // 삭제 여부
}
