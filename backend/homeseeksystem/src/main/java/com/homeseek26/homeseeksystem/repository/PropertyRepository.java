package com.homeseek26.homeseeksystem.repository;

import com.homeseek26.homeseeksystem.entity.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PropertyRepository extends JpaRepository<Property, Long> {
    
    // Find properties by owner ID
    List<Property> findByOwnerId(Long ownerId);
    
    // Find properties by availability status
    List<Property> findByAvailabilityStatus(String status);
    
    // Optional: Find properties by type
    List<Property> findByType(String type);
    
    // Optional: Search by name containing keyword
    List<Property> findByPropertyNameContainingIgnoreCase(String keyword);
}