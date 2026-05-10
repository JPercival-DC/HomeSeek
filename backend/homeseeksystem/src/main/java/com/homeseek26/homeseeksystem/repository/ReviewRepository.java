package com.homeseek26.homeseeksystem.repository;

import com.homeseek26.homeseeksystem.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    
    // Find reviews by user ID using JPQL
    List<Review> findByUser_UserId(Long userId);
    
    // Native query as fallback
    @Query(value = "SELECT * FROM reviews WHERE user_id = :userId", nativeQuery = true)
    List<Review> findReviewsByUserIdNative(@Param("userId") Long userId);
    
    // Find reviews by property ID
    List<Review> findByProperty_PropertyId(Long propertyId);
    
    // Get average rating for a property
    @Query("SELECT AVG(r.rating) FROM Review r WHERE r.property.propertyId = :propertyId")
    Double getAverageRatingByPropertyId(@Param("propertyId") Long propertyId);
    
    // Get review count for a property
    @Query("SELECT COUNT(r) FROM Review r WHERE r.property.propertyId = :propertyId")
    Long getReviewCountByPropertyId(@Param("propertyId") Long propertyId);
}