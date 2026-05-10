package com.homeseek26.homeseeksystem.service;

import com.homeseek26.homeseeksystem.entity.Review;
import com.homeseek26.homeseeksystem.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReviewService {
    
    @Autowired
    private ReviewRepository reviewRepository;
    
    // CREATE REVIEW - With validation and logging
    public Review createReview(Review review) {
        try {
            // Validate required fields
            if (review.getUser() == null || review.getUser().getUserId() == null) {
                throw new RuntimeException("User ID is required for review");
            }
            if (review.getProperty() == null || review.getProperty().getPropertyId() == null) {
                throw new RuntimeException("Property ID is required for review");
            }
            if (review.getRating() == null) {
                throw new RuntimeException("Rating is required for review");
            }
            
            review.setReviewDate(LocalDate.now());
            
            System.out.println("=== SAVING REVIEW ===");
            System.out.println("User ID: " + review.getUser().getUserId());
            System.out.println("Property ID: " + review.getProperty().getPropertyId());
            System.out.println("Rating: " + review.getRating());
            System.out.println("Comment: " + review.getComment());
            System.out.println("Review Date: " + review.getReviewDate());
            
            Review saved = reviewRepository.save(review);
            System.out.println("Review saved with ID: " + saved.getReviewID());
            
            return saved;
        } catch (Exception e) {
            System.err.println("Error saving review: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Failed to save review: " + e.getMessage());
        }
    }
    
    // GET REVIEW BY ID
    public Review getReviewById(Long id) {
        return reviewRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Review not found with id: " + id));
    }
    
    // GET ALL REVIEWS
    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }
    
    // UPDATE REVIEW
    public Review updateReview(Long id, Review updatedReview) {
        Review existing = getReviewById(id);
        existing.setRating(updatedReview.getRating());
        existing.setComment(updatedReview.getComment());
        return reviewRepository.save(existing);
    }
    
    // DELETE REVIEW
    public void deleteReview(Long id) {
        try {
            System.out.println("Deleting review with ID: " + id);
            reviewRepository.deleteById(id);
            System.out.println("Review deleted successfully");
        } catch (Exception e) {
            System.err.println("Error deleting review: " + e.getMessage());
            throw new RuntimeException("Failed to delete review: " + e.getMessage());
        }
    }
    
    // GET REVIEWS BY PROPERTY ID
    public List<Review> getReviewsByProperty(Long propertyId) {
        try {
            System.out.println("Fetching reviews for property ID: " + propertyId);
            List<Review> reviews = reviewRepository.findByProperty_PropertyId(propertyId);
            System.out.println("Found " + reviews.size() + " reviews for property " + propertyId);
            
            // Log each review found
            for (Review review : reviews) {
                System.out.println("Review ID: " + review.getReviewID() + 
                                   ", Rating: " + review.getRating() + 
                                   ", Comment: " + review.getComment());
            }
            
            return reviews;
        } catch (Exception e) {
            System.err.println("Error fetching reviews: " + e.getMessage());
            return List.of();
        }
    }
    
    // GET REVIEWS BY USER ID
    public List<Review> getReviewsByUser(Long userId) {
        try {
            System.out.println("=== SERVICE: Getting reviews for user: " + userId);
            
            // Try native query first
            List<Review> reviews = reviewRepository.findReviewsByUserIdNative(userId);
            System.out.println("Native query found: " + reviews.size() + " reviews");
            
            // Log each review
            for (Review review : reviews) {
                System.out.println("Review ID: " + review.getReviewID() + 
                                ", Rating: " + review.getRating() + 
                                ", Comment: " + review.getComment());
            }
            
            return reviews;
        } catch (Exception e) {
            System.err.println("Error fetching reviews: " + e.getMessage());
            e.printStackTrace();
            return List.of();
        }
    }
    
    // GET PROPERTY RATING (Average and Count)
    public Map<String, Object> getPropertyRating(Long propertyId) {
        try {
            System.out.println("Calculating rating for property ID: " + propertyId);
            
            List<Review> reviews = reviewRepository.findByProperty_PropertyId(propertyId);
            
            double average = 0.0;
            int count = reviews.size();
            
            if (count > 0) {
                double sum = reviews.stream().mapToDouble(Review::getRating).sum();
                average = sum / count;
            }
            
            Map<String, Object> ratingData = new HashMap<>();
            ratingData.put("average", Math.round(average * 10) / 10.0); // Round to 1 decimal
            ratingData.put("count", count);
            
            System.out.println("Property " + propertyId + " - Average: " + average + ", Count: " + count);
            
            return ratingData;
        } catch (Exception e) {
            System.err.println("Error calculating rating: " + e.getMessage());
            Map<String, Object> ratingData = new HashMap<>();
            ratingData.put("average", 0.0);
            ratingData.put("count", 0);
            return ratingData;
        }
    }
    
    // GET AVERAGE RATING ONLY
    public Double getAverageRating(Long propertyId) {
        try {
            Double avg = reviewRepository.getAverageRatingByPropertyId(propertyId);
            return avg != null ? Math.round(avg * 10) / 10.0 : 0.0;
        } catch (Exception e) {
            System.err.println("Error getting average rating: " + e.getMessage());
            return 0.0;
        }
    }
    
    // GET REVIEW COUNT ONLY
    public Long getReviewCount(Long propertyId) {
        try {
            Long count = reviewRepository.getReviewCountByPropertyId(propertyId);
            return count != null ? count : 0L;
        } catch (Exception e) {
            System.err.println("Error getting review count: " + e.getMessage());
            return 0L;
        }
    }
}