package com.springbootproject.restfulapp.repository;

import com.springbootproject.restfulapp.model.Review;
import com.springbootproject.restfulapp.model.ReviewStatus;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ReviewRepository extends CrudRepository<Review, Long> {
    List<Review> findReviewsByRestaurantIdAndStatus(Long restaurantId, ReviewStatus reviewStatus);
    List<Review> findReviewsByStatus(ReviewStatus reviewStatus);
}
