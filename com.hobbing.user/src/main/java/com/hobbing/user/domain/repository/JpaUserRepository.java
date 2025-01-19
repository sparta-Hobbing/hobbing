package com.hobbing.user.domain.repository;

import com.hobbing.user.domain.model.User;
<<<<<<< HEAD
import com.hobbing.user.infrastructure.PageInfo;
=======
>>>>>>> dev
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface JpaUserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByNickname(String nickname);

    Optional<User> findByIdAndDeletedAtIsNull(UUID id);

    Page<User> findAllByDeletedAtIsNull(Pageable pageInfo);

}
