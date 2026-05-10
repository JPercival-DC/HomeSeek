package com.homeseek26.homeseeksystem.repository;

import com.homeseek26.homeseeksystem.entity.BoardingHouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface BoardingHouseRepository extends JpaRepository<BoardingHouse, Long> {
    
    List<BoardingHouse> findByLocation(String location);
    
    List<BoardingHouse> findByAvailabilityStatus(String status);
    
    List<BoardingHouse> findByPriceBetween(Double minPrice, Double maxPrice);
    
    List<BoardingHouse> findByOwner_UserId(Long ownerId);
    
    @Query("SELECT b FROM BoardingHouse b WHERE b.name LIKE %:keyword% OR b.location LIKE %:keyword% OR b.address LIKE %:keyword%")
    List<BoardingHouse> searchByKeyword(@Param("keyword") String keyword);
    
    @Query("SELECT b FROM BoardingHouse b JOIN b.amenities a WHERE a.amenityName = :amenity")
    List<BoardingHouse> findByAmenityName(@Param("amenity") String amenity);
}