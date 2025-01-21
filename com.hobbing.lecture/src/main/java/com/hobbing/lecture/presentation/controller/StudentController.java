package com.hobbing.lecture.presentation.controller;

import com.hobbing.lecture.application.StudentService;
import com.hobbing.lecture.application.dto.StudentRequestDto;
import com.hobbing.lecture.application.dto.StudentResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @PostMapping("/{lectureId}")
    public ResponseEntity<StudentResponseDto> registerStudent(
            @PathVariable UUID lectureId,
            @RequestBody StudentRequestDto requestDto) {
        return ResponseEntity.ok(studentService.registerStudent(lectureId, requestDto));
    }

    @GetMapping("/lecture/{lectureId}")
    public ResponseEntity<List<StudentResponseDto>> getStudentsByLecture(@PathVariable UUID lectureId) {
        return ResponseEntity.ok(studentService.getStudentsByLecture(lectureId));
    }
}
