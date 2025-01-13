package com.project.RestaurantReviewPlatform.services.impl;

import com.project.RestaurantReviewPlatform.domain.entity.RestaurantEntity;
import com.project.RestaurantReviewPlatform.domain.entity.ReviewEntity;
import com.project.RestaurantReviewPlatform.domain.entity.UserEntity;
import com.project.RestaurantReviewPlatform.repositories.RestaurantRepository;
import com.project.RestaurantReviewPlatform.repositories.ReviewRepository;
import com.project.RestaurantReviewPlatform.repositories.UserRepository;
import com.project.RestaurantReviewPlatform.services.RestaurantService;
import com.project.RestaurantReviewPlatform.services.ReviewService;
import jakarta.persistence.EntityNotFoundException;
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
    private UserRepository userRepository;
    private RestaurantRepository restaurantRepository;

    public ReviewServiceImpl(ReviewRepository reviewRepository, RestaurantService restaurantService, UserRepository userRepository, RestaurantRepository restaurantRepository) {
        this.reviewRepository = reviewRepository;
        this.restaurantService = restaurantService;
        this.userRepository = userRepository;
        this.restaurantRepository = restaurantRepository;
    }

    @Override
    public ReviewEntity save(ReviewEntity review) {
        RestaurantEntity restaurant = restaurantRepository.findById(review.getRestaurant().getId())
                .orElseThrow(() -> new EntityNotFoundException("Restaurant not found"));

        UserEntity user = userRepository.findById(review.getUserEntity().getId())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        ReviewEntity reviewEntity = new ReviewEntity();
        reviewEntity.setRestaurant(restaurant);
        reviewEntity.setUserEntity(user);
        reviewEntity.setRating(review.getRating());
        reviewEntity.setDescription(review.getDescription());

        return reviewRepository.save(reviewEntity);
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
        return reviewRepository.findByRestaurantId(restaurantId);
    }

    @Override
    public List<ReviewEntity> listReviewByUserId(UUID userId) {
        return reviewRepository.findByUserEntityId(userId);
    }

    @Override
    public boolean isExist(UUID id) {
        return reviewRepository.existsById(id);
    }
}
