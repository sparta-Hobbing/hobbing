package com.hobbing.user.domain.repository;

import com.hobbing.user.domain.model.User;
import com.hobbing.user.infrastructure.PageInfo;
import org.springframework.data.domain.Page;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository {

    Optional<User> findByNickname(String nickname);

    User save(User user);

    Optional<User> findByIdAndDeletedAtIsNull(UUID id);

    Page<User> findUsers(PageInfo pageInfo);
}
