package com.hobbing.queue.controller;

import com.hobbing.common.application.dto.ApiResponse;
import com.hobbing.queue.application.QueueService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


@RestController
@RequestMapping("/queues")
@RequiredArgsConstructor
public class QueueController {

    private final QueueService queueService;


    @PostMapping("/{lectureId}/waiting-users")
    public ApiResponse<Integer> registerQueue(@PathVariable UUID lectureId,
                                              @RequestBody UUID userId) {

        int rank = queueService.registerQueue(lectureId, userId);

        return ApiResponse.ofSuccess(
                HttpStatus.CREATED, "대기 성공", rank
        );
    }

    @DeleteMapping("/{lectureId}/waiting-users/{userId}")
    public ApiResponse<Void> deregisterQueue(@PathVariable UUID lectureId,
                                        @PathVariable UUID userId) {

        queueService.deregisterQueue(lectureId, userId);

        return ApiResponse.ofSuccess(
                HttpStatus.OK, "대기 취소 성공", null
        );
    }


    public void exitReservationPage(UUID userId, UUID lectureId) {
    }

}