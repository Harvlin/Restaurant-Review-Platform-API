package com.project.RestaurantReviewPlatform.services;

import com.project.RestaurantReviewPlatform.domain.entity.ReviewEntity;
import java.util.List;
import java.util.UUID;

public interface ReviewService {

    ReviewEntity save(ReviewEntity reviewEntity);

    ReviewEntity partialUpdate(UUID id, ReviewEntity reviewEntity);

    void deleteReview(UUID id);

    ReviewEntity getReviewById(UUID id);

    List<ReviewEntity> getReviewsByRestaurant(UUID restaurantId);

    List<ReviewEntity> getReviewsByUser(UUID userId);
}
