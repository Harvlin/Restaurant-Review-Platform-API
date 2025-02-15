package com.project.RestaurantReviewPlatform.domain.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantDto {

    private UUID id;

    private String name;

    private String location;

    private String cuisineType;

    private String operatingHour;

    private String contactDetails;

    private Double avgRating;

}
