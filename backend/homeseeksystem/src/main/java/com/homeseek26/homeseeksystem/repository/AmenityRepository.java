package com.homeseek26.homeseeksystem.repository;

import com.homeseek26.homeseeksystem.entity.Amenity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface AmenityRepository extends JpaRepository<Amenity, Long> {
    
    Optional<Amenity> findByAmenityName(String amenityName);
    
    boolean existsByAmenityName(String amenityName);
}