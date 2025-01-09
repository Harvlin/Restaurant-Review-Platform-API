package com.project.RestaurantReviewPlatform.mappers.impl;

import com.project.RestaurantReviewPlatform.domain.dtos.RestaurantDto;
import com.project.RestaurantReviewPlatform.domain.entity.RestaurantEntity;
import com.project.RestaurantReviewPlatform.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RestaurantMapperImpl implements Mapper<RestaurantEntity, RestaurantDto> {
    private final ModelMapper modelMapper;

    @Autowired
    public RestaurantMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public RestaurantDto mapTo(RestaurantEntity restaurantEntity) {
        return modelMapper.map(restaurantEntity, RestaurantDto.class);
    }

    @Override
    public RestaurantEntity mapFrom(RestaurantDto restaurantDto) {
        return modelMapper.map(restaurantDto, RestaurantEntity.class);
    }
}
