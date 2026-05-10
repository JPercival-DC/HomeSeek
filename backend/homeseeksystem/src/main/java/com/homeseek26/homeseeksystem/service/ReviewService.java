package com.homeseek26.homeseeksystem.service;

import com.homeseek26.homeseeksystem.entity.Review;
import com.homeseek26.homeseeksystem.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
public class ReviewService {
    
    @Autowired
    private ReviewRepository reviewRepository;
    
    public Review createReview(Review review) {
        review.setReviewDate(LocalDate.now());
        return reviewRepository.save(review);
    }
    
    public Review getReviewById(Long id) {
        return reviewRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Review not found with id: " + id));
    }
    
    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }
    
    public Review updateReview(Long id, Review updatedReview) {
        Review existing = getReviewById(id);
        existing.setRating(updatedReview.getRating());
        existing.setComment(updatedReview.getComment());
        return reviewRepository.save(existing);
    }
    
    public void deleteReview(Long id) {
        reviewRepository.deleteById(id);
    }
    
    public List<Review> getReviewsByBoardingHouse(Long boardingId) {
        return reviewRepository.findByBoardingHouse_BoardingIDOrderByReviewDateDesc(boardingId);
    }
    
    public List<Review> getReviewsByUser(Long userId) {
        return reviewRepository.findByUser_UserId(userId);
    }
    
    public Double getAverageRating(Long boardingId) {
        Double avg = reviewRepository.getAverageRatingByBoardingId(boardingId);
        return avg != null ? avg : 0.0;
    }
}