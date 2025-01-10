package com.project.RestaurantReviewPlatform.services;

import com.project.RestaurantReviewPlatform.domain.entity.ReviewEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ReviewService {

    ReviewEntity save(ReviewEntity reviewEntity);

    ReviewEntity partialUpdate(UUID id, ReviewEntity reviewEntity);

    void deleteReview(UUID id);

    Page<ReviewEntity> findAll(Pageable pageable);

    Optional<ReviewEntity> getReviewById(UUID id);

    List<ReviewEntity> listReviewByRestaurantId(UUID restaurantId);

    List<ReviewEntity> listReviewByUserId(UUID userId);

    boolean isExist(UUID id);
}
