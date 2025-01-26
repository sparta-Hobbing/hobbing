package com.hobbing.lecture.presentation.controller;

import com.hobbing.lecture.application.LectureService;
import com.hobbing.lecture.application.dto.LectureRequestDto;
import com.hobbing.lecture.application.dto.LectureResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
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

    @GetMapping("/available")
    public ResponseEntity<List<LectureResponseDto>> getAvailableLectures(@RequestParam LocalDateTime startTime) {
        return ResponseEntity.ok(lectureService.findLecturesStartingAfter(startTime));
    }

    @GetMapping("/search")
    public ResponseEntity<Page<LectureResponseDto>> searchLectures(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String status,
            Pageable pageable) {
        return ResponseEntity.ok(lectureService.searchLectures(title, status, pageable));
    }

    @GetMapping("/calendar")
    public ResponseEntity<List<LectureResponseDto>> getLecturesInDateRange(
            @RequestParam LocalDateTime startDate,
            @RequestParam LocalDateTime endDate) {
        return ResponseEntity.ok(lectureService.findLecturesInDateRange(startDate, endDate));
    }
}
