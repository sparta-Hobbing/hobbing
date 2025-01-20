package com.hobbing.queue.controller;

import com.hobbing.queue.service.QueueService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/queue")
public class QueueController {

    private final QueueService queueService;

    public QueueController(QueueService queueService) {
        this.queueService = queueService;
    }

    // 대기열 입장
    @PostMapping("/enter/{lectureId}")
    public ResponseEntity<String> enterQueue(@PathVariable String lectureId, @RequestParam String userId) {
        boolean success = queueService.enterQueueWithLock(lectureId, userId);
        if (success) {
            return ResponseEntity.ok("User successfully added to queue for lecture: " + lectureId);
        } else {
            return ResponseEntity.status(429).body("Queue is busy. Please try again later.");
        }
    }

    // 대기열 상태 조회
    @GetMapping("/status/{lectureId}")
    public ResponseEntity<List<Object>> getQueueStatus(@PathVariable String lectureId) {
        return ResponseEntity.ok(queueService.getQueueStatus(lectureId));
    }

    // 대기열 나가기
    @DeleteMapping("/leave/{lectureId}")
    public ResponseEntity<String> leaveQueue(@PathVariable String lectureId, @RequestParam String userId) {
        queueService.leaveQueue(lectureId, userId);
        return ResponseEntity.ok("User removed from queue for lecture: " + lectureId);
    }

    // TTL 설정
    @PostMapping("/set-ttl/{lectureId}")
    public ResponseEntity<String> setQueueTTL(@PathVariable String lectureId, @RequestParam long ttlInSeconds) {
        boolean success = queueService.setQueueTTL(lectureId, ttlInSeconds);
        if (success) {
            return ResponseEntity.ok("TTL set for queue: " + lectureId);
        } else {
            return ResponseEntity.status(404).body("Queue not found for lecture: " + lectureId);
        }
    }
}
