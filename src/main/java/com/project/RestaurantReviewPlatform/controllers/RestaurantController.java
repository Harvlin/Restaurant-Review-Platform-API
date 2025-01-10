package com.project.RestaurantReviewPlatform.controllers;

import com.project.RestaurantReviewPlatform.domain.dtos.RestaurantDto;
import com.project.RestaurantReviewPlatform.domain.entity.RestaurantEntity;
import com.project.RestaurantReviewPlatform.mappers.Mapper;
import com.project.RestaurantReviewPlatform.services.RestaurantService;
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
@RequestMapping(path = "/api/v1/restaurants")
public class RestaurantController {

    private RestaurantService restaurantService;
    private Mapper<RestaurantEntity, RestaurantDto> restaurantMapper;

    @Autowired
    public RestaurantController(RestaurantService restaurantService, Mapper<RestaurantEntity, RestaurantDto> restaurantMapper) {
        this.restaurantService = restaurantService;
        this.restaurantMapper = restaurantMapper;
    }

    @PostMapping
    public ResponseEntity<RestaurantDto> createRestaurant(@RequestBody RestaurantDto restaurantDto) {
        RestaurantEntity restaurantEntity = restaurantMapper.mapFrom(restaurantDto);
        RestaurantEntity savedRestaurant = restaurantService.save(restaurantEntity);

        return new ResponseEntity<>(restaurantMapper.mapTo(savedRestaurant), HttpStatus.CREATED);
    }

    @GetMapping
    public Page<RestaurantDto> listRestaurant(Pageable pageable) {
        Page<RestaurantEntity> restaurantEntities = restaurantService.findAll(pageable);
        return restaurantEntities.map(restaurantMapper::mapTo);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestaurantDto> findOneById(@PathVariable("id")UUID id) {
        Optional<RestaurantEntity> foundRestaurant = restaurantService.getRestaurantById(id);
        return foundRestaurant.map(restaurantEntity -> {
            RestaurantDto restaurantDto = restaurantMapper.mapTo(restaurantEntity);
            return new ResponseEntity<>(restaurantDto, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/{name}")
    public ResponseEntity<List<RestaurantDto>> listRestaurantByName(@PathVariable("name") String name) {
        List<RestaurantEntity> list = restaurantService.listRestaurantByName(name);
        return new ResponseEntity<>(list.stream().map(restaurantMapper::mapTo).toList(), HttpStatus.OK);
    }

    @GetMapping("/{cuisine}")
    public ResponseEntity<List<RestaurantDto>> listRestaurantByCuisine(@PathVariable("cuisine") String cuisine) {
        List<RestaurantEntity> list = restaurantService.listRestaurantByCuisine(cuisine);
        return new ResponseEntity<>(list.stream().map(restaurantMapper::mapTo).toList(), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RestaurantDto> fullUpdateRestaurant(@PathVariable("id") UUID id, @RequestBody RestaurantDto restaurantDto) {
        if (!restaurantService.isExist(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        restaurantDto.setId(id);
        RestaurantEntity restaurantEntity = restaurantMapper.mapFrom(restaurantDto);
        RestaurantEntity savedRestaurantEntity = restaurantService.save(restaurantEntity);
        return new ResponseEntity<>(restaurantMapper.mapTo(savedRestaurantEntity), HttpStatus.OK);
    }

    @PatchMapping(path = "/{id}")
    public ResponseEntity<RestaurantDto> partialUpdateRestaurant(@PathVariable("id") UUID id, @RequestBody RestaurantDto restaurantDto) {
        if (!restaurantService.isExist(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        RestaurantEntity restaurantEntity = restaurantMapper.mapFrom(restaurantDto);
        RestaurantEntity savedRestaurant = restaurantService.partialUpdateRestaurant(id, restaurantEntity);

        return new ResponseEntity<>(restaurantMapper.mapTo(savedRestaurant), HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity deleteRestaurant(@PathVariable("id") UUID id) {
        if (!restaurantService.isExist(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        restaurantService.deleteRestaurant(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
