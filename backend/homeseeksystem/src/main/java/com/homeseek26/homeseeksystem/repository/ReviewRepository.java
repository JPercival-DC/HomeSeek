package com.homeseek26.homeseeksystem.repository;

import com.homeseek26.homeseeksystem.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    
    List<Review> findByBoardingHouse_BoardingID(Long boardingID);
    
    List<Review> findByUser_UserId(Long userId);
    
    @Query("SELECT AVG(r.rating) FROM Review r WHERE r.boardingHouse.boardingID = :boardingId")
    Double getAverageRatingByBoardingId(@Param("boardingId") Long boardingId);
    
    List<Review> findByBoardingHouse_BoardingIDOrderByReviewDateDesc(Long boardingID);
}