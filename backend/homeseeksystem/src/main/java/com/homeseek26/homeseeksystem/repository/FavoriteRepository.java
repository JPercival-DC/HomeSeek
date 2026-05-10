package com.homeseek26.homeseeksystem.repository;

import com.homeseek26.homeseeksystem.entity.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
    
    List<Favorite> findByUser_UserId(Long userId);
    
    List<Favorite> findByBoardingHouse_BoardingID(Long boardingID);
    
    Optional<Favorite> findByUser_UserIdAndBoardingHouse_BoardingID(Long userId, Long boardingID);
    
    boolean existsByUser_UserIdAndBoardingHouse_BoardingID(Long userId, Long boardingID);
    
    void deleteByUser_UserIdAndBoardingHouse_BoardingID(Long userId, Long boardingID);
}