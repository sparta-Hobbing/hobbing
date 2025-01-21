package com.hobbing.lecture.application;

import com.hobbing.lecture.application.dto.LectureRequestDto;
import com.hobbing.lecture.application.dto.LectureResponseDto;
import com.hobbing.lecture.domain.model.Lecture;
import com.hobbing.lecture.domain.repository.LectureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

        Lecture updatedLecture = lectureRepository.save(lecture);
        return toResponseDto(updatedLecture);
    }

    public void deleteLecture(UUID id) {
        Lecture lecture = lectureRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Lecture not found with id: " + id));
        lecture.setDeleted(true);
        lectureRepository.save(lecture);
    }

    private LectureResponseDto toResponseDto(Lecture lecture) {
        LectureResponseDto response = new LectureResponseDto();
        response.setId(lecture.getId());
        response.setTitle(lecture.getTitle());
        response.setDescription(lecture.getDescription());
        response.setPrice(lecture.getPrice());
        response.setMaxParticipants(lecture.getMaxParticipants());
        response.setLocation(lecture.getLocation());
        response.setStatus(lecture.getStatus());
        return response;
    }
}
