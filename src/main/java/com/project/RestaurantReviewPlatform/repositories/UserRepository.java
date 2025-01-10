package com.project.RestaurantReviewPlatform.repositories;

import com.project.RestaurantReviewPlatform.domain.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, UUID> {
    Optional<UserEntity> findByName(String name);
    List<UserEntity> findAllByName(String name);
}
