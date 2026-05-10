package com.homeseek26.homeseeksystem.repository;

import com.homeseek26.homeseeksystem.entity.Photo;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PhotoRepository extends JpaRepository<Photo, Long> {
    
    List<Photo> findByBoardingHouse_BoardingID(Long boardingID);
    
    void deleteByBoardingHouse_BoardingID(Long boardingID);
}