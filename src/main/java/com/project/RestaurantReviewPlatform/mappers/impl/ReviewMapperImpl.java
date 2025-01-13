package com.project.RestaurantReviewPlatform.mappers.impl;

import com.project.RestaurantReviewPlatform.domain.dtos.ReviewDto;
import com.project.RestaurantReviewPlatform.domain.entity.RestaurantEntity;
import com.project.RestaurantReviewPlatform.domain.entity.ReviewEntity;
import com.project.RestaurantReviewPlatform.domain.entity.UserEntity;
import com.project.RestaurantReviewPlatform.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ReviewMapperImpl implements Mapper<ReviewEntity, ReviewDto> {
    private ModelMapper modelMapper;

    public ReviewMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;

        modelMapper.typeMap(ReviewEntity.class, ReviewDto.class)
                .addMappings(mapper -> {
                    mapper.map(src -> src.getRestaurant().getId(), (dest, value) -> dest.getRest().setId((UUID) value));
                    mapper.map(src -> src.getRestaurant().getName(), (dest, value) -> dest.getRest().setName((String) value));
                });
    }

    @Override
    public ReviewDto mapTo(ReviewEntity reviewEntity) {
        return modelMapper.map(reviewEntity, ReviewDto.class);
    }

    @Override
    public ReviewEntity mapFrom(ReviewDto reviewDto) {
        ReviewEntity reviewEntity = new ReviewEntity();
        RestaurantEntity restaurant = new RestaurantEntity();
        UserEntity user = new UserEntity();

        if (reviewDto.getRest() != null) {
            restaurant.setId(reviewDto.getRest().getId());
            reviewEntity.setRestaurant(restaurant);
        }
        if (reviewDto.getUserDto() != null) {
            user.setId(reviewDto.getUserDto().getId());
            reviewEntity.setUserEntity(user);
        }

        reviewEntity.setRating(reviewDto.getRating());
        reviewEntity.setDescription(reviewDto.getDescription());
        return reviewEntity;
    }
}
