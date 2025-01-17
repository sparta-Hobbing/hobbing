package com.hobbing.lecture.domain.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
public class LectureSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id; // 일정 ID

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lecture_id", nullable = false)
    private Lecture lecture; // 강의와 연관된 일정

    @Column(nullable = false)
    private LocalDateTime startTime; // 강의 시작 시간

    @Column(nullable = false)
    private LocalDateTime endTime; // 강의 종료 시간

    @Column(nullable = false)
    private String status = "AVAILABLE"; // 일정 상태 (AVAILABLE, FULL, CANCELLED)

    @Column(nullable = false)
    private boolean isDeleted = false; // 삭제 여부
}
