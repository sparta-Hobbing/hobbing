package com.hobbing.queue.domain;

import java.time.LocalDateTime;

public class QueueEntry {

    private String userId;
    private LocalDateTime joinedAt;

    public QueueEntry(String userId, LocalDateTime joinedAt) {
        this.userId = userId;
        this.joinedAt = joinedAt;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public LocalDateTime getJoinedAt() {
        return joinedAt;
    }

    public void setJoinedAt(LocalDateTime joinedAt) {
        this.joinedAt = joinedAt;
    }
}
