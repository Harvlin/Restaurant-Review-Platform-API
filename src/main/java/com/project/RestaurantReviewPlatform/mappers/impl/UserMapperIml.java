package com.project.RestaurantReviewPlatform.mappers.impl;

import com.project.RestaurantReviewPlatform.domain.dtos.UserDto;
import com.project.RestaurantReviewPlatform.domain.entity.UserEntity;
import com.project.RestaurantReviewPlatform.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserMapperIml implements Mapper<UserEntity, UserDto> {
    private ModelMapper modelMapper;

    @Autowired
    public UserMapperIml(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public UserDto mapTo(UserEntity userEntity) {
        return modelMapper.map(userEntity, UserDto.class);
    }

    @Override
    public UserEntity mapFrom(UserDto userDto) {
        return modelMapper.map(userDto, UserEntity.class);
    }
}
