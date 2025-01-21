package com.hobbing.lecture.application;

import com.hobbing.lecture.domain.model.Lecture;
import com.hobbing.lecture.domain.model.LectureSchedule;
import com.hobbing.lecture.domain.repository.LectureRepository;
import com.hobbing.lecture.domain.repository.LectureScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LectureScheduleService {

    private final LectureScheduleRepository scheduleRepository;
    private final LectureRepository lectureRepository;

    public LectureSchedule createSchedule(UUID lectureId, LocalDateTime startTime, LocalDateTime endTime) {
        Lecture lecture = lectureRepository.findById(lectureId)
                .orElseThrow(() -> new IllegalArgumentException("Lecture not found with id: " + lectureId));

        LectureSchedule schedule = new LectureSchedule();
        schedule.setLecture(lecture);
        schedule.setStartTime(startTime);
        schedule.setEndTime(endTime);

        return scheduleRepository.save(schedule);
    }

    public List<LectureSchedule> getSchedulesByLecture(UUID lectureId) {
        return scheduleRepository.findAllByLectureId(lectureId);
    }

    public LectureSchedule updateSchedule(UUID scheduleId, LocalDateTime startTime, LocalDateTime endTime, String status) {
        LectureSchedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new IllegalArgumentException("Schedule not found with id: " + scheduleId));

        schedule.setStartTime(startTime);
        schedule.setEndTime(endTime);
        schedule.setStatus(status);

        return scheduleRepository.save(schedule);
    }

    public void deleteSchedule(UUID scheduleId) {
        LectureSchedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new IllegalArgumentException("Schedule not found with id: " + scheduleId));

        schedule.setDeleted(true);
        scheduleRepository.save(schedule);
    }
}
