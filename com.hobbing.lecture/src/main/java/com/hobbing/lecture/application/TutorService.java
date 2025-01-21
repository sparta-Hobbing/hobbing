package com.hobbing.lecture.application;

import com.hobbing.lecture.application.dto.TutorRequestDto;
import com.hobbing.lecture.application.dto.TutorResponseDto;
import com.hobbing.lecture.domain.model.Tutor;
import com.hobbing.lecture.domain.repository.TutorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TutorService {

    private final TutorRepository tutorRepository;

    public TutorResponseDto createTutor(TutorRequestDto requestDto) {
        Tutor tutor = new Tutor();
        tutor.setName(requestDto.getName());
        tutor.setEmail(requestDto.getEmail());
        tutor.setBio(requestDto.getBio());

        Tutor savedTutor = tutorRepository.save(tutor);
        return toResponseDto(savedTutor);
    }

    public List<TutorResponseDto> getAllTutors() {
        return tutorRepository.findAll().stream()
                .map(this::toResponseDto)
                .collect(Collectors.toList());
    }

    public TutorResponseDto getTutorById(UUID id) {
        Tutor tutor = tutorRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Tutor not found with id: " + id));
        return toResponseDto(tutor);
    }

    public TutorResponseDto updateTutor(UUID id, TutorRequestDto requestDto) {
        Tutor tutor = tutorRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Tutor not found with id: " + id));

        tutor.setName(requestDto.getName());
        tutor.setEmail(requestDto.getEmail());
        tutor.setBio(requestDto.getBio());

        Tutor updatedTutor = tutorRepository.save(tutor);
        return toResponseDto(updatedTutor);
    }

    public void deleteTutor(UUID id) {
        Tutor tutor = tutorRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Tutor not found with id: " + id));
        tutor.setDeleted(true);
        tutorRepository.save(tutor);
    }

    private TutorResponseDto toResponseDto(Tutor tutor) {
        TutorResponseDto response = new TutorResponseDto();
        response.setId(tutor.getId());
        response.setName(tutor.getName());
        response.setEmail(tutor.getEmail());
        response.setBio(tutor.getBio());
        response.setRating(tutor.getRating());
        response.setReviewCount(tutor.getReviewCount());
        return response;
    }
}
