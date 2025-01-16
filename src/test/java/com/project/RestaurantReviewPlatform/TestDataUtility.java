package com.project.RestaurantReviewPlatform;

import com.project.RestaurantReviewPlatform.domain.dtos.UserDto;
import com.project.RestaurantReviewPlatform.domain.entity.UserEntity;

import java.util.UUID;

public class TestDataUtility {

    public static UserEntity createTestUserA() {
        return UserEntity.builder()
                .name("User A")
                .description("a desc for user A")
                .build();
    }

    public static UserDto createTestUserDtoA() {
        return UserDto.builder()
                .id(UUID.randomUUID())
                .name("User A")
                .description("a desc for user A")
                .build();
    }
}
