package com.project.RestaurantReviewPlatform.controllers;

import com.project.RestaurantReviewPlatform.domain.dtos.UserDto;
import com.project.RestaurantReviewPlatform.domain.entity.UserEntity;
import com.project.RestaurantReviewPlatform.mappers.Mapper;
import com.project.RestaurantReviewPlatform.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/v1/users")
public class UserController {

    private UserService userService;
    private Mapper<UserEntity, UserDto> userMapper;

    @Autowired
    public UserController(UserService userService, Mapper<UserEntity, UserDto> userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) {
        UserEntity user = userMapper.mapFrom(userDto);
        UserEntity savedUserEntity = userService.save(user);
        return new ResponseEntity<>(userMapper.mapTo(savedUserEntity), HttpStatus.CREATED);
    }

    @GetMapping
    public Page<UserDto> listUser(Pageable pageable) {
        Page<UserEntity> userEntities = userService.findAll(pageable);
        return userEntities.map(userMapper::mapTo);
    }

    @GetMapping(path = "/id/{id}")
    public ResponseEntity<UserDto> findOne(@PathVariable("id") UUID id) {
        Optional<UserEntity> foundUser = userService.findOne(id);
        return foundUser.map(userEntity -> {
            UserDto userDto = userMapper.mapTo(userEntity);
            return new ResponseEntity<>(userDto, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping(path = "/name/{name}")
    public ResponseEntity<List<UserDto>> findByName(@PathVariable("name") String name) {
        List<UserEntity> foundUser = userService.findByName(name);
        return new ResponseEntity<>(foundUser.stream().map(userMapper::mapTo).toList(), HttpStatus.OK);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<UserDto> fullUpdateUser(@PathVariable("id") UUID id, @RequestBody UserDto userDto) {
        if (!userService.isExist(id)) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        userDto.setId(id);
        UserEntity userEntity = userMapper.mapFrom(userDto);
        UserEntity savedUserEntity = userService.save(userEntity);
        return new ResponseEntity<>(userMapper.mapTo(savedUserEntity), HttpStatus.OK);
    }

    @PatchMapping(path = "/{id}")
    public ResponseEntity<UserDto> partialUpdateUser(@PathVariable("id") UUID id, @RequestBody UserDto userDto) {
        if (!userService.isExist(id)) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        UserEntity userEntity = userMapper.mapFrom(userDto);
        UserEntity savedUserEntity = userService.partialUpdate(id, userEntity);
        return new ResponseEntity<>(userMapper.mapTo(savedUserEntity), HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity deleteUser(@PathVariable("id") UUID id) {
        if (!userService.isExist(id)) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        userService.deleteUser(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
