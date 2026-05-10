package com.homeseek26.homeseeksystem.repository;

import com.homeseek26.homeseeksystem.entity.BookingRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BookingRequestRepository extends JpaRepository<BookingRequest, Long> {
    
    List<BookingRequest> findByTenant_UserId(Long userId);
    
    List<BookingRequest> findByBoardingHouse_BoardingID(Long boardingID);
    
    List<BookingRequest> findByBoardingHouse_Owner_UserId(Long ownerId);
    
    List<BookingRequest> findByStatus(String status);
    
    List<BookingRequest> findByTenant_UserIdAndStatus(Long userId, String status);
}