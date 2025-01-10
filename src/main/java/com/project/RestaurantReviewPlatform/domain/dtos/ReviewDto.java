package com.project.RestaurantReviewPlatform.domain.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDto {

    private UUID id;

    private RestaurantDto rest;

    private UserDto userDto;

    private Double rating;

    private String description;

}
