package com.hobbing.user.infrastructure.repository;

import com.hobbing.user.domain.model.User;
import com.hobbing.user.domain.repository.JpaUserRepository;
import com.hobbing.user.domain.repository.UserRepository;
import com.hobbing.user.infrastructure.PageInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

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
    public Page<User> findUsers(PageInfo pageInfo) {
        return jpaUserRepository.findAllByDeletedAtIsNull(pageInfo.toPageRequest());
    }

}
