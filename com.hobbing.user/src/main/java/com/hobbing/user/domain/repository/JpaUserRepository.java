package com.hobbing.user.domain.repository;

import com.hobbing.user.domain.model.User;
import com.hobbing.user.presentation.dto.PageInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

public interface JpaUserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByNickname(String nickname);

    Optional<User> findByIdAndDeletedAtIsNull(UUID id);


    @Query("SELECT u FROM User u WHERE u.createdAt BETWEEN :startDate AND :endDate")
    Page<User> findAllByDeletedAtIsNull(@Param("startDate") LocalDateTime startDate,
                                        @Param("endDate")LocalDateTime endDate,
                                        Pageable pageInfo);

}