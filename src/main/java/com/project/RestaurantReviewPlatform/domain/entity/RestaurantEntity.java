package com.project.RestaurantReviewPlatform.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantEntity {
    @Id
    @GeneratedValue
    private UUID id;

    private String name;

    private String location;

    private String cuisineType;

    private String operatingHour;

    private String contactDetails;

    private Double avgRating;
}

