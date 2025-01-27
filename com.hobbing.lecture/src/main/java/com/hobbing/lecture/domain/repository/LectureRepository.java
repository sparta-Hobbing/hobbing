package com.hobbing.lecture.domain.repository;

import com.hobbing.lecture.domain.model.Lecture;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface LectureRepository extends JpaRepository<Lecture, UUID> {

    // 강의 시작 시간 이후의 강의 검색
    List<Lecture> findAllByStartDateTimeAfter(LocalDateTime startTime);

    // 강의 제목 및 상태 기반 검색 (페이징 지원)
    Page<Lecture> findByTitleContainingIgnoreCaseAndStatusContainingIgnoreCase(String title, String status, Pageable pageable);

    // 특정 시간 범위 내 강의 검색
    List<Lecture> findAllByStartDateTimeBetween(LocalDateTime startDate, LocalDateTime endDate);
}
