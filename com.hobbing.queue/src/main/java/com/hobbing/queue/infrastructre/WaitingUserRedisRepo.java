package com.hobbing.queue.infrastructre;


import com.hobbing.queue.domain.model.WaitingUser;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;


public interface WaitingUserRedisRepo extends CrudRepository<WaitingUser, UUID> {
}
