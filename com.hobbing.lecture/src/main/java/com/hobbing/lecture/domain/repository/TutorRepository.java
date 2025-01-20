package com.hobbing.lecture.domain.repository;

import com.hobbing.lecture.domain.model.Tutor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TutorRepository extends JpaRepository<Tutor, UUID> {
}
