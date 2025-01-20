package com.hobbing.lecture.application;

import com.hobbing.lecture.application.dto.StudentRequestDto;
import com.hobbing.lecture.application.dto.StudentResponseDto;
import com.hobbing.lecture.domain.model.Lecture;
import com.hobbing.lecture.domain.model.Student;
import com.hobbing.lecture.domain.repository.LectureRepository;
import com.hobbing.lecture.domain.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;
    private final LectureRepository lectureRepository;

    public StudentResponseDto registerStudent(UUID lectureId, StudentRequestDto requestDto) {
        Lecture lecture = lectureRepository.findById(lectureId)
                .orElseThrow(() -> new IllegalArgumentException("Lecture not found with id: " + lectureId));

        Student student = new Student();
        student.setName(requestDto.getName());
        student.setEmail(requestDto.getEmail());
        student.setLecture(lecture);

        return toResponseDto(studentRepository.save(student));
    }

    public List<StudentResponseDto> getStudentsByLecture(UUID lectureId) {
        return studentRepository.findAllByLectureId(lectureId).stream()
                .map(this::toResponseDto)
                .collect(Collectors.toList());
    }

    private StudentResponseDto toResponseDto(Student student) {
        StudentResponseDto response = new StudentResponseDto();
        response.setId(student.getId());
        response.setName(student.getName());
        response.setEmail(student.getEmail());
        response.setLectureId(student.getLecture().getId());
        response.setIsAttended(student.getIsAttended());
        response.setIsCompleted(student.getIsCompleted());
        return response;
    }
}
