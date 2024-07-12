package com.explore.reviewms.review.service;

import com.explore.reviewms.review.model.Review;

import java.util.List;

public interface ReviewService {

    List<Review> findAll(Long companyId);
    boolean createReview(Long companyId,Review review);
    Review getReview(Long reviewId);
    boolean deleteReview(Long reviewId);
    boolean updateReview(Long reviewId, Review updatedReview);
}
