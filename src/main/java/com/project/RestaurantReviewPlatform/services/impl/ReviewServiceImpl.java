package com.project.RestaurantReviewPlatform.services.impl;

import com.project.RestaurantReviewPlatform.domain.entity.ReviewEntity;
import com.project.RestaurantReviewPlatform.repositories.ReviewRepository;
import com.project.RestaurantReviewPlatform.services.RestaurantService;
import com.project.RestaurantReviewPlatform.services.ReviewService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ReviewServiceImpl implements ReviewService {

    private ReviewRepository reviewRepository;
    private RestaurantService restaurantService;

    public ReviewServiceImpl(ReviewRepository reviewRepository, RestaurantService restaurantService) {
        this.reviewRepository = reviewRepository;
        this.restaurantService = restaurantService;
    }

    @Override
    public ReviewEntity save(ReviewEntity review) {
        ReviewEntity reviewEntity = reviewRepository.save(review);
        restaurantService.updateAverageRating(review.getRestaurant().getId());
        return reviewEntity;
    }

    @Override
    public ReviewEntity partialUpdate(UUID id, ReviewEntity reviewEntity) {
        reviewEntity.setId(id);

        return reviewRepository.findById(id).map(existingReview -> {
            Optional.ofNullable(reviewEntity.getRestaurant()).ifPresent(existingReview::setRestaurant);
            Optional.ofNullable(reviewEntity.getDescription()).ifPresent(existingReview::setDescription);
            Optional.ofNullable(reviewEntity.getRating()).ifPresent(existingReview::setRating);
            Optional.ofNullable(reviewEntity.getUserEntity()).ifPresent(existingReview::setUserEntity);

            restaurantService.updateAverageRating(reviewEntity.getRestaurant().getId());
            return reviewRepository.save(existingReview);
        }).orElseThrow(() -> new RuntimeException("Restaurant doesn't exist"));
    }

    @Override
    public void deleteReview(UUID id) {
        if (reviewRepository.existsById(id)) {
            reviewRepository.deleteById(id);
        } else {
            throw new RuntimeException("Restaurant not found");
        }
    }

    @Override
    public Page<ReviewEntity> findAll(Pageable pageable) {
        return reviewRepository.findAll(pageable);
    }

    @Override
    public Optional<ReviewEntity> getReviewById(UUID id) {
        return reviewRepository.findById(id);
    }

    @Override
    public List<ReviewEntity> listReviewByRestaurantId(UUID restaurantId) {
        return reviewRepository.findByRestaurant_Id(restaurantId);
    }

    @Override
    public List<ReviewEntity> listReviewByUserId(UUID userId) {
        return reviewRepository.findByUserEntity_Id(userId);
    }

    @Override
    public boolean isExist(UUID id) {
        return reviewRepository.existsById(id);
    }
}
