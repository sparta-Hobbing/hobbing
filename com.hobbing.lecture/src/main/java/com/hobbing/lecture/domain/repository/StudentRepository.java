package com.hobbing.lecture.domain.repository;

import com.hobbing.lecture.domain.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface StudentRepository extends JpaRepository<Student, UUID> {
    List<Student> findAllByLectureId(UUID lectureId);
}
