package com.springbootproject.restfulapp.controller;

import com.springbootproject.restfulapp.model.Restaurant;
import com.springbootproject.restfulapp.model.Review;
import com.springbootproject.restfulapp.model.ReviewStatus;
import com.springbootproject.restfulapp.model.User;
import com.springbootproject.restfulapp.repository.RestaurantRepository;
import com.springbootproject.restfulapp.repository.ReviewRepository;
import com.springbootproject.restfulapp.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RequestMapping("/reviews")
@RestController
public class ReviewController {
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;

    public ReviewController(ReviewRepository reviewRepository, UserRepository userRepository, RestaurantRepository restaurantRepository) {
        this.reviewRepository = reviewRepository;
        this.userRepository = userRepository;
        this.restaurantRepository = restaurantRepository;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void addUserReview(@RequestBody Review review) {
        validateUserReview(review);

        Optional<Restaurant> optionalRestaurant = restaurantRepository.findById(review.getRestaurantId());
        if (optionalRestaurant.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY);
        }

        review.setStatus(ReviewStatus.PENDING);
        reviewRepository.save(review);
    }

    private void validateUserReview(Review review) {
        if (ObjectUtils.isEmpty(review.getSubmittedBy())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        if (ObjectUtils.isEmpty(review.getRestaurantId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        if (ObjectUtils.isEmpty(review.getPeanutScore()) &&
                ObjectUtils.isEmpty(review.getDairyScore()) &&
                ObjectUtils.isEmpty(review.getEggScore())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        Optional<User> optionalUser = userRepository.findUserByDisplayName(review.getSubmittedBy());
        if (optionalUser.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }
}
