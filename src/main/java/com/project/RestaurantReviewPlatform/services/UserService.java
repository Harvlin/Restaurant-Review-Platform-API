package com.project.RestaurantReviewPlatform.services;

import com.project.RestaurantReviewPlatform.domain.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserService {

    UserEntity save(UserEntity userEntity);

    Page<UserEntity> findAll(Pageable pageable);

    Optional<UserEntity> findOne(UUID id);

    List<UserEntity> findByName(String name);

    UserEntity partialUpdate(UUID id, UserEntity userEntity);

    void deleteUser(UUID id);

    UserEntity getUserById(UUID uuid);

    boolean isExist(UUID id);
}
