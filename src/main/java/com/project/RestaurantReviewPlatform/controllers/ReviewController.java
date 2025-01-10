package com.project.RestaurantReviewPlatform.controllers;

import com.project.RestaurantReviewPlatform.domain.dtos.ReviewDto;
import com.project.RestaurantReviewPlatform.domain.entity.ReviewEntity;
import com.project.RestaurantReviewPlatform.mappers.Mapper;
import com.project.RestaurantReviewPlatform.services.ReviewService;
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
@RequestMapping(path = "/api/v1/reviews")
public class ReviewController {

    private ReviewService reviewService;
    private Mapper<ReviewEntity, ReviewDto> reviewMapper;

    @Autowired
    public ReviewController(ReviewService reviewService, Mapper<ReviewEntity, ReviewDto> reviewMapper) {
        this.reviewService = reviewService;
        this.reviewMapper = reviewMapper;
    }

    @PostMapping
    public ResponseEntity<ReviewDto> createRestaurant(@RequestBody ReviewDto restaurantDto) {
        ReviewEntity reviewEntity = reviewMapper.mapFrom(restaurantDto);
        ReviewEntity savedReview = reviewService.save(reviewEntity);

        return new ResponseEntity<>(reviewMapper.mapTo(savedReview), HttpStatus.CREATED);
    }

    @GetMapping
    public Page<ReviewDto> listRestaurant(Pageable pageable) {
        Page<ReviewEntity> reviewEntities = reviewService.findAll(pageable);
        return reviewEntities.map(reviewMapper::mapTo);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReviewDto> getOneById(@PathVariable("id") UUID id) {
        Optional<ReviewEntity> foundReview = reviewService.getReviewById(id);
        return foundReview.map(reviewEntity -> {
            ReviewDto reviewDto = reviewMapper.mapTo(reviewEntity);
            return new ResponseEntity<>(reviewDto, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<ReviewDto>> listReviewByRestaurantId(@PathVariable("id") UUID id) {
        List<ReviewEntity> foundReview = reviewService.listReviewByRestaurantId(id);
        return new ResponseEntity<>(foundReview.stream().map(reviewMapper::mapTo).toList(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<ReviewDto>> listReviewByUserId(@PathVariable("id") UUID id) {
        List<ReviewEntity> foundReview = reviewService.listReviewByUserId(id);
        return new ResponseEntity<>(foundReview.stream().map(reviewMapper::mapTo).toList(), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReviewDto> fullUpdateReview(@PathVariable("id") UUID id, @RequestBody ReviewDto reviewDto) {
        if (!reviewService.isExist(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        reviewDto.setId(id);
        ReviewEntity reviewEntity = reviewMapper.mapFrom(reviewDto);
        ReviewEntity savedReview = reviewService.save(reviewEntity);
        return new ResponseEntity<>(reviewMapper.mapTo(savedReview), HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ReviewDto> partialUpdateReview(@PathVariable("id") UUID id, @RequestBody ReviewDto reviewDto) {
        if (!reviewService.isExist(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        ReviewEntity reviewEntity = reviewMapper.mapFrom(reviewDto);
        ReviewEntity savedReview = reviewService.partialUpdate(id, reviewEntity);

        return new ResponseEntity<>(reviewMapper.mapTo(savedReview), HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity deleteUser(@PathVariable("id") UUID id) {
        if (!reviewService.isExist(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        reviewService.deleteReview(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
