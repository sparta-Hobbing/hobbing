package com.hobbing.queue.service;

import com.hobbing.queue.domain.QueueEntry;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class QueueService {

    private final RedisTemplate<String, Object> redisTemplate;

    public QueueService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    // 대기열에 사용자 추가
    public int enterQueue(UUID lectureId, UUID userId) {
        QueueEntry entry = new QueueEntry(userId, LocalDateTime.now());
        redisTemplate.opsForList().rightPush("queue:" + lectureId, entry);
    }

    // 대기열 상태 조회
    public List<Object> getQueueStatus(String lectureId) {
        List<Object> queue = redisTemplate.opsForList().range("queue:" + lectureId, 0, -1);
        return queue != null ? queue : List.of();
    }

    // 대기열에서 사용자 제거
    public void leaveQueue(UUID lectureId, UUID userId) {
        List<Object> queue = redisTemplate.opsForList().range("queue:" + lectureId, 0, -1);
        if (queue != null) {
            for (Object obj : queue) {
                QueueEntry entry = (QueueEntry) obj;
                if (entry.getUserId().equals(userId)) {
                    redisTemplate.opsForList().remove("queue:" + lectureId, 1, entry);
                    break;
                }
            }
        }
    }

    // 대기열 TTL 설정
    public boolean setQueueTTL(String lectureId, long ttlInSeconds) {
        String key = "queue:" + lectureId;
        Boolean exists = redisTemplate.hasKey(key);
        if (Boolean.TRUE.equals(exists)) {
            redisTemplate.expire(key, Duration.ofSeconds(ttlInSeconds));
            return true;
        }
        return false;
    }

    // 동시성 제어된 대기열 입장
    public boolean enterQueueWithLock(String lectureId, String userId) {
        String lockKey = "lock:queue:" + lectureId;
        boolean lockAcquired = false;
        try {
            lockAcquired = Boolean.TRUE.equals(redisTemplate.opsForValue().setIfAbsent(lockKey, "LOCK", Duration.ofSeconds(3)));
            if (lockAcquired) {
                enterQueue(lectureId, userId);
                return true;
            } else {
                return false;
            }
        } finally {
            if (lockAcquired) {
                redisTemplate.delete(lockKey);
            }
        }
    }
}
