package com.hobbing.lecture.domain.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
public class Tutor {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id; // 강사 ID

    @Column(nullable = false)
    private String name; // 강사 이름

    @Column(nullable = false, unique = true)
    private String email; // 강사 이메일

    @Column(columnDefinition = "TEXT")
    private String bio; // 강사 소개

    @Column(nullable = false)
    private double rating = 0.0; // 강사 평점

    @Column(nullable = false)
    private int reviewCount = 0; // 리뷰 수

    @Column(nullable = false)
    private boolean isDeleted = false; // 삭제 여부
}
