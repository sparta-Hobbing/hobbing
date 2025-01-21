package com.hobbing.lecture.presentation.controller;

import com.hobbing.lecture.application.LectureService;
import com.hobbing.lecture.application.dto.LectureRequestDto;
import com.hobbing.lecture.application.dto.LectureResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/lectures")
@RequiredArgsConstructor
public class LectureController {

    private final LectureService lectureService;

    @PostMapping
    public ResponseEntity<LectureResponseDto> createLecture(@RequestBody LectureRequestDto requestDto) {
        return ResponseEntity.ok(lectureService.createLecture(requestDto));
    }

    @GetMapping
    public ResponseEntity<List<LectureResponseDto>> getAllLectures() {
        return ResponseEntity.ok(lectureService.getAllLectures());
    }

    @GetMapping("/{id}")
    public ResponseEntity<LectureResponseDto> getLectureById(@PathVariable UUID id) {
        return ResponseEntity.ok(lectureService.getLectureById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<LectureResponseDto> updateLecture(
            @PathVariable UUID id,
            @RequestBody LectureRequestDto requestDto) {
        return ResponseEntity.ok(lectureService.updateLecture(id, requestDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLecture(@PathVariable UUID id) {
        lectureService.deleteLecture(id);
        return ResponseEntity.noContent().build();
    }
}
