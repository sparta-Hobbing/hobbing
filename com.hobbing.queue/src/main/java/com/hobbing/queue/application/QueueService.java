package com.hobbing.queue.application;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class QueueService {

    private final RedisTemplate<String, Object> redisTemplate;


    public int registerQueue(UUID lectureId, UUID userId) {
        linkSse(userId, lectureId);
        return 0;
    }

    public List<Object> getQueueStatus(UUID lectureId) {
        return null;
    }

    public void deregisterQueue(UUID lectureId, UUID userId) {
        unlinkSse(userId, lectureId);
    }


    protected void linkSse(UUID userId, UUID lectureId) {
    }

    protected void unlinkSse(UUID userId, UUID lectureId) {
    }

    // 사용자의 입장여부 api로 받아서 하면 네트워크 통신현황에 따라 예약 페이지 인원현황과 동시성 문제 가능성, 순번되면 알아서 enter 이후 사용자 요청에 따라 취소
    protected void enterReservationPage(UUID userId, UUID lectureId) {
    }

    public void exitReservationPage(UUID userId, UUID lectureId) {
    }
}