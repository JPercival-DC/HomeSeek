package com.homeseek26.homeseeksystem.repository;

import com.homeseek26.homeseeksystem.entity.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
    
    // Find favorites by user ID
    List<Favorite> findByUser_UserId(Long userId);
    
    // Find favorites by property ID
    List<Favorite> findByProperty_PropertyId(Long propertyId);
    
    // Find favorites by boarding house ID
    List<Favorite> findByBoardingHouse_BoardingID(Long boardingID);
    
    // Find specific favorite by user and property
    Optional<Favorite> findByUser_UserIdAndProperty_PropertyId(Long userId, Long propertyId);
    
    // Find specific favorite by user and boarding house
    Optional<Favorite> findByUser_UserIdAndBoardingHouse_BoardingID(Long userId, Long boardingID);
    
    // Check if favorite exists for user and property
    boolean existsByUser_UserIdAndProperty_PropertyId(Long userId, Long propertyId);
    
    // Check if favorite exists for user and boarding house
    boolean existsByUser_UserIdAndBoardingHouse_BoardingID(Long userId, Long boardingID);
    
    // Delete favorite by user and property
    @Modifying
    @Transactional
    @Query("DELETE FROM Favorite f WHERE f.user.userId = :userId AND f.property.propertyId = :propertyId")
    void deleteByUser_UserIdAndProperty_PropertyId(@Param("userId") Long userId, @Param("propertyId") Long propertyId);
    
    // Delete favorite by user and boarding house
    @Modifying
    @Transactional
    @Query("DELETE FROM Favorite f WHERE f.user.userId = :userId AND f.boardingHouse.boardingID = :boardingId")
    void deleteByUser_UserIdAndBoardingHouse_BoardingID(@Param("userId") Long userId, @Param("boardingId") Long boardingId);
    
    // Count favorites for a property
    int countByProperty_PropertyId(Long propertyId);
    
    // Count favorites for a boarding house
    int countByBoardingHouse_BoardingID(Long boardingID);
}