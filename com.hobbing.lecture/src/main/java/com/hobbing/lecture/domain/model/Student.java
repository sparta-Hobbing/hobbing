package com.hobbing.lecture.domain.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id; // 학생 ID

    @Column(nullable = false)
    private String name; // 학생 이름

    @Column(nullable = false, unique = true)
    private String email; // 학생 이메일

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lecture_id", nullable = false)
    private Lecture lecture; // 학생이 수강 중인 강의

    @Column(nullable = false)
    private Boolean isAttended = false; // 출석 여부

    @Column(nullable = false)
    private Boolean isCompleted = false; // 완료 여부
}
