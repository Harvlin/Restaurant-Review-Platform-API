package com.project.RestaurantReviewPlatform.repositories;

import com.project.RestaurantReviewPlatform.domain.entity.RestaurantEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

@Repository
public interface RestaurantRepository extends JpaRepository<RestaurantEntity, UUID> {
    List<RestaurantEntity> findByNameContainingIgnoreCase(String name);

    List<RestaurantEntity> findByCuisineTypeContainingIgnoreCase(String cuisineType);
}
