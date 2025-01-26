package com.hobbing.lecture.application;

import com.hobbing.lecture.application.dto.LectureRequestDto;
import com.hobbing.lecture.application.dto.LectureResponseDto;
import com.hobbing.lecture.domain.model.Lecture;
import com.hobbing.lecture.domain.repository.LectureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LectureService {

    private final LectureRepository lectureRepository;

    public LectureResponseDto createLecture(LectureRequestDto requestDto) {
        Lecture lecture = new Lecture();
        lecture.setTitle(requestDto.getTitle());
        lecture.setDescription(requestDto.getDescription());
        lecture.setPrice(requestDto.getPrice());
        lecture.setMaxParticipants(requestDto.getMaxParticipants());
        lecture.setLocation(requestDto.getLocation());
        lecture.setStartDateTime(requestDto.getStartDateTime());
        lecture.setEndDateTime(requestDto.getEndDateTime());

        Lecture savedLecture = lectureRepository.save(lecture);
        return toResponseDto(savedLecture);
    }

    public List<LectureResponseDto> getAllLectures() {
        return lectureRepository.findAll()
                .stream()
                .map(this::toResponseDto)
                .collect(Collectors.toList());
    }

    public LectureResponseDto getLectureById(UUID id) {
        Lecture lecture = lectureRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Lecture not found with id: " + id));

        // Null-safe 처리
        if (lecture.getStartDateTime() == null) {
            lecture.setStartDateTime(LocalDateTime.now());
        }
        if (lecture.getEndDateTime() == null) {
            lecture.setEndDateTime(LocalDateTime.now().plusHours(1));
        }

        return toResponseDto(lecture);
    }

    public LectureResponseDto updateLecture(UUID id, LectureRequestDto requestDto) {
        Lecture lecture = lectureRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Lecture not found with id: " + id));

        lecture.setTitle(requestDto.getTitle());
        lecture.setDescription(requestDto.getDescription());
        lecture.setPrice(requestDto.getPrice());
        lecture.setMaxParticipants(requestDto.getMaxParticipants());
        lecture.setLocation(requestDto.getLocation());
        lecture.setStartDateTime(requestDto.getStartDateTime());
        lecture.setEndDateTime(requestDto.getEndDateTime());

        Lecture updatedLecture = lectureRepository.save(lecture);
        return toResponseDto(updatedLecture);
    }

    public void deleteLecture(UUID id) {
        Lecture lecture = lectureRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Lecture not found with id: " + id));
        lecture.setDeleted(true);
        lectureRepository.save(lecture);
    }

    public List<LectureResponseDto> findLecturesStartingAfter(LocalDateTime startTime) {
        return lectureRepository.findAllByStartDateTimeAfter(startTime)
                .stream()
                .map(this::toResponseDto)
                .collect(Collectors.toList());
    }

    public Page<LectureResponseDto> searchLectures(String title, String status, Pageable pageable) {
        return lectureRepository.findByTitleContainingIgnoreCaseAndStatusContainingIgnoreCase(title, status, pageable)
                .map(this::toResponseDto);
    }

    public List<LectureResponseDto> findLecturesInDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return lectureRepository.findAllByStartDateTimeBetween(startDate, endDate)
                .stream()
                .map(this::toResponseDto)
                .collect(Collectors.toList());
    }

    private LectureResponseDto toResponseDto(Lecture lecture) {
        return LectureResponseDto.builder()
                .id(lecture.getId())
                .title(lecture.getTitle())
                .description(lecture.getDescription())
                .price(lecture.getPrice())
                .maxParticipants(lecture.getMaxParticipants())
                .location(lecture.getLocation())
                .startDateTime(lecture.getStartDateTime())
                .endDateTime(lecture.getEndDateTime())
                .status(lecture.getStatus())
                .build();
    }
}
