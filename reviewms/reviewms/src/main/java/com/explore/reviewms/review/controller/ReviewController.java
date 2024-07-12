package com.explore.reviewms.review.controller;

import com.explore.reviewms.review.dto.ReviewMessageProducer;
import com.explore.reviewms.review.model.Review;
import com.explore.reviewms.review.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private ReviewMessageProducer reviewMessageProducer;

    @GetMapping
    public ResponseEntity<List<Review>> getAllReviewsofCompany(@RequestParam Long companyId){
        return new ResponseEntity<>(reviewService.findAll(companyId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> addReview(@RequestParam Long companyId, @RequestBody Review review){
        boolean isCreated = reviewService.createReview(companyId,review);
        if(isCreated){
            reviewMessageProducer.sendMessage(review);
            return new ResponseEntity<>("Review created successfully",HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{reviewId}")
    public ResponseEntity<Review> getReviewById(@PathVariable Long reviewId){
        return new ResponseEntity<>(reviewService.getReview(reviewId),HttpStatus.OK);
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<String> deleteReviewById(@PathVariable Long reviewId){
        boolean isDeleted = reviewService.deleteReview(reviewId);
        if(isDeleted){
            return new ResponseEntity<>("Review deleted successfully",HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{reviewId}")
    public ResponseEntity<String> updateReviewById(@PathVariable Long reviewId,@RequestBody Review review){
        boolean isUpdated = reviewService.updateReview(reviewId,review);
        if(isUpdated){
            return new ResponseEntity<>("Review updated successfully",HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/averageRating")
    public double getAverageReview(@RequestParam Long companyId){
        List<Review> reviews = reviewService.findAll(companyId);
        return reviews.stream().mapToDouble(Review::getRating).average().orElse(0.0);
    }
}
