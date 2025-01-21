package com.hobbing.lecture.presentation.controller;

import com.hobbing.lecture.application.LectureScheduleService;
import com.hobbing.lecture.application.dto.LectureScheduleRequestDto;
import com.hobbing.lecture.application.dto.LectureScheduleResponseDto;
import com.hobbing.lecture.domain.model.LectureSchedule;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/lectures/{lectureId}/schedules")
@RequiredArgsConstructor
public class LectureScheduleController {

    private final LectureScheduleService scheduleService;

    @PostMapping
    public LectureScheduleResponseDto createSchedule(@PathVariable UUID lectureId,
                                                     @RequestBody LectureScheduleRequestDto requestDto) {
        LectureSchedule schedule = scheduleService.createSchedule(lectureId, requestDto.getStartTime(), requestDto.getEndTime());
        return toResponseDto(schedule);
    }

    @GetMapping
    public List<LectureScheduleResponseDto> getSchedules(@PathVariable UUID lectureId) {
        return scheduleService.getSchedulesByLecture(lectureId).stream()
                .map(this::toResponseDto)
                .toList();
    }

    @PutMapping("/{scheduleId}")
    public LectureScheduleResponseDto updateSchedule(@PathVariable UUID scheduleId,
                                                     @RequestBody LectureScheduleRequestDto requestDto,
                                                     @RequestParam String status) {
        LectureSchedule schedule = scheduleService.updateSchedule(scheduleId, requestDto.getStartTime(), requestDto.getEndTime(), status);
        return toResponseDto(schedule);
    }

    @DeleteMapping("/{scheduleId}")
    public void deleteSchedule(@PathVariable UUID scheduleId) {
        scheduleService.deleteSchedule(scheduleId);
    }

    private LectureScheduleResponseDto toResponseDto(LectureSchedule schedule) {
        LectureScheduleResponseDto response = new LectureScheduleResponseDto();
        response.setId(schedule.getId());
        response.setLectureId(schedule.getLecture().getId());
        response.setStartTime(schedule.getStartTime());
        response.setEndTime(schedule.getEndTime());
        response.setStatus(schedule.getStatus());
        return response;
    }
}
