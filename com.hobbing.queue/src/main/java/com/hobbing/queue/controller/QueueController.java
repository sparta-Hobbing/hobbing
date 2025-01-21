package com.hobbing.queue.controller;

import com.hobbing.common.application.dto.ApiResponse;
import com.hobbing.queue.service.QueueService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


@RestController
@RequestMapping("/queues")
@RequiredArgsConstructor
public class QueueController {

    private final QueueService queueService;


    @PostMapping("/{lectureId}/awaiters")
    public ApiResponse<Integer> enterQueue(@PathVariable UUID lectureId,
                                           @RequestBody UUID userId) {

        int rank = queueService.enterQueue(lectureId, userId);

        return ApiResponse.ofSuccess(
                HttpStatus.CREATED, "대기 성공", rank
        );
    }

    @DeleteMapping("/{lectureId}/awaiters/{userId}")
    public ApiResponse<Void> leaveQueue(@PathVariable UUID lectureId,
                                        @PathVariable UUID userId) {

        queueService.leaveQueue(lectureId, userId);

        return ApiResponse.ofSuccess(
                HttpStatus.OK, "대기 취소 성공", null
        );
    }

}