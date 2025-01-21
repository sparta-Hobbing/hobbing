package com.hobbing.lecture.domain.repository;

import com.hobbing.lecture.domain.model.Lecture;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LectureRepository extends JpaRepository<Lecture, UUID> {
}
