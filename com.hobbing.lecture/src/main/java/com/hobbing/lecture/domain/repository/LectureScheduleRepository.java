package com.hobbing.lecture.domain.repository;

import com.hobbing.lecture.domain.model.LectureSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface LectureScheduleRepository extends JpaRepository<LectureSchedule, UUID> {
    List<LectureSchedule> findAllByLectureId(UUID lectureId);
}
