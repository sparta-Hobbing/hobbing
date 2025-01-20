package com.hobbing.user.domain.repository;

import com.hobbing.user.domain.model.User;
import com.hobbing.user.presentation.dto.PageInfo;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository {

    Optional<User> findByNickname(String nickname);

    User save(User user);

    Optional<User> findByIdAndDeletedAtIsNull(UUID id);

    Page<User> findUsers(LocalDateTime startDate, LocalDateTime endDate, PageInfo pageInfo);
}
