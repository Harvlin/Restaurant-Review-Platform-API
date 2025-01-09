package com.project.RestaurantReviewPlatform.services;

import com.project.RestaurantReviewPlatform.domain.entity.RestaurantEntity;
import java.util.List;
import java.util.UUID;

public interface RestaurantService {

    RestaurantEntity save(RestaurantEntity restaurantEntity);

    RestaurantEntity partialUpdateRestaurant(UUID id, RestaurantEntity restaurantEntity);

    void deleteRestaurant(UUID id);

    RestaurantEntity getRestaurantById(UUID id);

    List<RestaurantEntity> searchRestaurantsByName(String name);

    List<RestaurantEntity> searchRestaurantsByCuisine(String cuisine);

    void updateAverageRating(UUID id);

}
