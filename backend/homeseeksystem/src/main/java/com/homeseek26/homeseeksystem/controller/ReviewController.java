package com.homeseek26.homeseeksystem.controller;

import com.homeseek26.homeseeksystem.entity.Review;
import com.homeseek26.homeseeksystem.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/reviews")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:5173"})
public class ReviewController {
    
    @Autowired
    private ReviewService reviewService;
    
    @PostMapping
    public Review createReview(@RequestBody Review review) {
        return reviewService.createReview(review);
    }
    
    @GetMapping("/{id}")
    public Review getReviewById(@PathVariable Long id) {
        return reviewService.getReviewById(id);
    }
    
    @GetMapping
    public List<Review> getAllReviews() {
        return reviewService.getAllReviews();
    }
    
    @PutMapping("/{id}")
    public Review updateReview(@PathVariable Long id, @RequestBody Review review) {
        return reviewService.updateReview(id, review);
    }
    
    @DeleteMapping("/{id}")
    public String deleteReview(@PathVariable Long id) {
        reviewService.deleteReview(id);
        return "Review deleted successfully";
    }
    
    @GetMapping("/boarding-house/{boardingId}")
    public List<Review> getReviewsByBoardingHouse(@PathVariable Long boardingId) {
        return reviewService.getReviewsByBoardingHouse(boardingId);
    }
    
    @GetMapping("/user/{userId}")
    public List<Review> getReviewsByUser(@PathVariable Long userId) {
        return reviewService.getReviewsByUser(userId);
    }
    
    @GetMapping("/boarding-house/{boardingId}/average-rating")
    public Double getAverageRating(@PathVariable Long boardingId) {
        return reviewService.getAverageRating(boardingId);
    }
}