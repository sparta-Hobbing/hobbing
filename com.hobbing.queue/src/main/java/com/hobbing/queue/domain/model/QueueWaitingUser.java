package com.hobbing.queue.domain.model;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.time.LocalDateTime;
import java.util.UUID;


@RedisHash("waiting_user")
@RequiredArgsConstructor
@Getter
public class QueueWaitingUser {
    @Id
    protected final UUID userId;
    protected int rank;
    protected LocalDateTime registeredAt;
}
