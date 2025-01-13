package com.project.RestaurantReviewPlatform.repositories;

import com.project.RestaurantReviewPlatform.domain.entity.ReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

@Repository
public interface ReviewRepository extends JpaRepository<ReviewEntity, UUID> {
    List<ReviewEntity> findByRestaurantId(UUID restaurantId);

    List<ReviewEntity> findByUserEntityId(UUID userId);

    @Query("SELECT AVG(r.rating) FROM ReviewEntity r WHERE r.restaurant.id = :restaurantId")
    Double findAverageRatingByRestaurantId(@Param("restaurantId") UUID restaurantId);
}
