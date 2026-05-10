package com.homeseek26.homeseeksystem.controller;

import com.homeseek26.homeseeksystem.entity.Review;
import com.homeseek26.homeseeksystem.entity.UserEntity;
import com.homeseek26.homeseeksystem.entity.Property;
import com.homeseek26.homeseeksystem.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/reviews")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:5173"})
public class ReviewController {
    
    @Autowired
    private ReviewService reviewService;
    
    @PostMapping
    public ResponseEntity<?> createReview(
            @RequestParam(required = false) Long userId,
            @RequestParam(required = false) Long propertyId,
            @RequestParam(required = false) Integer rating,
            @RequestParam(required = false) String comment,
            @RequestBody(required = false) Map<String, Object> requestBody) {
        try {
            Long finalUserId = userId;
            Long finalPropertyId = propertyId;
            Integer finalRating = rating;
            String finalComment = comment;
            
            // If query params are not provided, try to get from body
            if (finalUserId == null && requestBody != null) {
                if (requestBody.containsKey("userId")) {
                    finalUserId = Long.valueOf(requestBody.get("userId").toString());
                } else if (requestBody.containsKey("user") && requestBody.get("user") instanceof Map) {
                    Map<String, Object> user = (Map<String, Object>) requestBody.get("user");
                    finalUserId = Long.valueOf(user.get("userId").toString());
                }
            }
            
            if (finalPropertyId == null && requestBody != null) {
                if (requestBody.containsKey("propertyId")) {
                    finalPropertyId = Long.valueOf(requestBody.get("propertyId").toString());
                } else if (requestBody.containsKey("property") && requestBody.get("property") instanceof Map) {
                    Map<String, Object> property = (Map<String, Object>) requestBody.get("property");
                    finalPropertyId = Long.valueOf(property.get("propertyId").toString());
                }
            }
            
            if (finalRating == null && requestBody != null && requestBody.containsKey("rating")) {
                finalRating = Integer.valueOf(requestBody.get("rating").toString());
            }
            
            if (finalComment == null && requestBody != null && requestBody.containsKey("comment")) {
                finalComment = requestBody.get("comment").toString();
            }
            
            if (finalUserId == null || finalPropertyId == null || finalRating == null) {
                Map<String, String> error = new HashMap<>();
                error.put("error", "userId, propertyId, and rating are required");
                return ResponseEntity.badRequest().body(error);
            }
            
            System.out.println("Creating review - userId: " + finalUserId + ", propertyId: " + finalPropertyId + ", rating: " + finalRating);
            
            Review review = new Review();
            
            UserEntity user = new UserEntity();
            user.setUserId(finalUserId);
            review.setUser(user);
            
            Property property = new Property();
            property.setPropertyId(finalPropertyId);
            review.setProperty(property);
            
            review.setRating(finalRating);
            review.setComment(finalComment);
            review.setReviewDate(LocalDate.now());
            
            Review saved = reviewService.createReview(review);
            
            System.out.println("Review saved with ID: " + saved.getReviewID() + ", propertyId: " + saved.getProperty().getPropertyId());
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Review submitted successfully");
            response.put("reviewId", saved.getReviewID());
            
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
            
        } catch (Exception e) {
            e.printStackTrace();
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }
    
    @GetMapping("/property/{propertyId}")
    public ResponseEntity<?> getReviewsByProperty(@PathVariable Long propertyId) {
        try {
            System.out.println("Fetching reviews for propertyId: " + propertyId);
            List<Review> reviews = reviewService.getReviewsByProperty(propertyId);
            System.out.println("Found " + reviews.size() + " reviews");
            return ResponseEntity.ok(reviews);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.ok(List.of());
        }
    }
    
    // GET REVIEWS BY USER ID - ADD THIS ENDPOINT
    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getReviewsByUser(@PathVariable Long userId) {
        try {
            System.out.println("=== GETTING REVIEWS FOR USER: " + userId);
            List<Review> reviews = reviewService.getReviewsByUser(userId);
            System.out.println("Found " + reviews.size() + " reviews");
            
            // Log each review for debugging
            for (Review review : reviews) {
                System.out.println("Review ID: " + review.getReviewID() + 
                                   ", Property ID: " + (review.getProperty() != null ? review.getProperty().getPropertyId() : "null") +
                                   ", Rating: " + review.getRating() +
                                   ", Comment: " + review.getComment());
            }
            
            return ResponseEntity.ok(reviews);
        } catch (Exception e) {
            System.err.println("Error fetching reviews: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.ok(List.of());
        }
    }
    
    @GetMapping("/property/{propertyId}/rating")
    public ResponseEntity<?> getPropertyRating(@PathVariable Long propertyId) {
        try {
            Map<String, Object> ratingData = reviewService.getPropertyRating(propertyId);
            return ResponseEntity.ok(ratingData);
        } catch (Exception e) {
            return ResponseEntity.ok(Map.of("average", 0.0, "count", 0));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteReview(@PathVariable Long id) {
        try {
            reviewService.deleteReview(id);
            Map<String, String> response = new HashMap<>();
            response.put("success", "true");
            response.put("message", "Review deleted successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}