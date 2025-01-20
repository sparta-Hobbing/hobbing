package com.hobbing.lecture.presentation.controller;

import com.hobbing.lecture.application.TutorService;
import com.hobbing.lecture.application.dto.TutorRequestDto;
import com.hobbing.lecture.application.dto.TutorResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/tutors")
@RequiredArgsConstructor
public class TutorController {

    private final TutorService tutorService;

    @PostMapping
    public TutorResponseDto createTutor(@RequestBody TutorRequestDto requestDto) {
        return tutorService.createTutor(requestDto);
    }

    @GetMapping
    public List<TutorResponseDto> getAllTutors() {
        return tutorService.getAllTutors();
    }

    @GetMapping("/{id}")
    public TutorResponseDto getTutorById(@PathVariable UUID id) {
        return tutorService.getTutorById(id);
    }

    @PutMapping("/{id}")
    public TutorResponseDto updateTutor(@PathVariable UUID id, @RequestBody TutorRequestDto requestDto) {
        return tutorService.updateTutor(id, requestDto);
    }

    @DeleteMapping("/{id}")
    public void deleteTutor(@PathVariable UUID id) {
        tutorService.deleteTutor(id);
    }
}
