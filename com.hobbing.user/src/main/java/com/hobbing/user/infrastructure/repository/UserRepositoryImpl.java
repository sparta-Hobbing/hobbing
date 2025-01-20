package com.hobbing.user.infrastructure.repository;

import com.hobbing.user.domain.model.User;
import com.hobbing.user.domain.repository.JpaUserRepository;
import com.hobbing.user.domain.repository.UserRepository;
import com.hobbing.user.presentation.dto.PageInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final JpaUserRepository jpaUserRepository;

    @Override
    public Optional<User> findByNickname(String nickname) {
        return jpaUserRepository.findByNickname(nickname);
    }

    @Override
    public User save(User user) {
        return jpaUserRepository.save(user);
    }

    public Optional<User> findByIdAndDeletedAtIsNull(UUID id) {
        return jpaUserRepository.findByIdAndDeletedAtIsNull(id);
    }

    @Override
    public Page<User> findUsers(LocalDateTime startDate, LocalDateTime endDate, PageInfo pageInfo) {
        return jpaUserRepository.findAllByDeletedAtIsNull(startDate, endDate, pageInfo.toPageRequest());
    }

}
