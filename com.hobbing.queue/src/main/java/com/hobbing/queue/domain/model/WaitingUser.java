package com.hobbing.queue.domain.model;


import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.util.UUID;


@RedisHash("waiting_user")
@RequiredArgsConstructor
public class WaitingUser {
    @Id
    protected final UUID userId;
}
