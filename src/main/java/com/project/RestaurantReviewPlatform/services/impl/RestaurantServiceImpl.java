package com.project.RestaurantReviewPlatform.services.impl;

import com.project.RestaurantReviewPlatform.domain.entity.RestaurantEntity;
import com.project.RestaurantReviewPlatform.repositories.RestaurantRepository;
import com.project.RestaurantReviewPlatform.repositories.ReviewRepository;
import com.project.RestaurantReviewPlatform.services.RestaurantService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class RestaurantServiceImpl implements RestaurantService {
    private RestaurantRepository restaurantRepository;
    private ReviewRepository reviewRepository;

    public RestaurantServiceImpl(RestaurantRepository restaurantRepository, ReviewRepository reviewRepository) {
        this.restaurantRepository = restaurantRepository;
        this.reviewRepository = reviewRepository;
    }

    @Override
    public RestaurantEntity save(RestaurantEntity restaurantEntity) {
        return restaurantRepository.save(restaurantEntity);
    }

    @Override
    public RestaurantEntity partialUpdateRestaurant(UUID id, RestaurantEntity restaurantEntity) {
        restaurantEntity.setId(id);

        return restaurantRepository.findById(id).map(existingRestaurant -> {
            Optional.ofNullable(restaurantEntity.getName()).ifPresent(existingRestaurant::setName);
            Optional.ofNullable(restaurantEntity.getLocation()).ifPresent(existingRestaurant::setLocation);
            Optional.ofNullable(restaurantEntity.getCuisineType()).ifPresent(existingRestaurant::setCuisineType);
            Optional.ofNullable(restaurantEntity.getOperatingHour()).ifPresent(existingRestaurant::setOperatingHour);
            Optional.ofNullable(restaurantEntity.getContactDetails()).ifPresent(existingRestaurant::setContactDetails);

            return restaurantRepository.save(existingRestaurant);
        }).orElseThrow(() -> new RuntimeException("Restaurant doesn't exist"));
    }

    @Override
    public void deleteRestaurant(UUID id) {
        restaurantRepository.deleteById(id);
    }

    @Override
    public Page<RestaurantEntity> findAll(Pageable pageable) {
        return restaurantRepository.findAll(pageable);
    }

    @Override
    public Optional<RestaurantEntity> getRestaurantById(UUID id) {
        return restaurantRepository.findById(id);
    }

    @Override
    public List<RestaurantEntity> searchRestaurantsByName(String name) {
        return restaurantRepository.findByNameContainingIgnoreCase(name);
    }

    @Override
    public List<RestaurantEntity> searchRestaurantsByCuisine(String cuisine) {
        return restaurantRepository.findByCuisineTypeContainingIgnoreCase(cuisine);
    }

    @Override
    public void updateAverageRating(UUID id) {
        Double avgRating = reviewRepository.findAverageRatingByRestaurantId(id);
        RestaurantEntity restaurantEntity = restaurantRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Restaurant not found"));
        restaurantEntity.setAvgRating(avgRating);
        restaurantRepository.save(restaurantEntity);
    }

    @Override
    public boolean isExist(UUID id) {
        return restaurantRepository.existsById(id);
    }
}
