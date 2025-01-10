package com.project.RestaurantReviewPlatform.services;

import com.project.RestaurantReviewPlatform.domain.entity.RestaurantEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RestaurantService {

    RestaurantEntity save(RestaurantEntity restaurantEntity);

    RestaurantEntity partialUpdateRestaurant(UUID id, RestaurantEntity restaurantEntity);

    Page<RestaurantEntity> findAll(Pageable pageable);

    void deleteRestaurant(UUID id);

    Optional<RestaurantEntity> getRestaurantById(UUID id);

    List<RestaurantEntity> searchRestaurantsByName(String name);

    List<RestaurantEntity> searchRestaurantsByCuisine(String cuisine);

    void updateAverageRating(UUID id);

    boolean isExist(UUID id);
}
