package com.homeseek26.homeseeksystem.service;

import com.homeseek26.homeseeksystem.entity.BookingRequest;
import com.homeseek26.homeseeksystem.repository.BookingRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class BookingRequestService {
    
    @Autowired
    private BookingRequestRepository bookingRequestRepository;
    
    public BookingRequest createBookingRequest(BookingRequest bookingRequest) {
        bookingRequest.setRequestDate(LocalDateTime.now());
        bookingRequest.setStatus("PENDING");
        return bookingRequestRepository.save(bookingRequest);
    }
    
    public BookingRequest getBookingRequestById(Long id) {
        return bookingRequestRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Booking request not found with id: " + id));
    }
    
    public List<BookingRequest> getAllBookingRequests() {
        return bookingRequestRepository.findAll();
    }
    
    public BookingRequest updateBookingRequestStatus(Long id, String status) {
        BookingRequest existing = getBookingRequestById(id);
        existing.setStatus(status);
        return bookingRequestRepository.save(existing);
    }
    
    public void deleteBookingRequest(Long id) {
        bookingRequestRepository.deleteById(id);
    }
    
    public List<BookingRequest> getByTenant(Long tenantId) {
        return bookingRequestRepository.findByTenant_UserId(tenantId);
    }
    
    public List<BookingRequest> getByBoardingHouse(Long boardingId) {
        return bookingRequestRepository.findByBoardingHouse_BoardingID(boardingId);
    }
    
    public List<BookingRequest> getByOwner(Long ownerId) {
        return bookingRequestRepository.findByBoardingHouse_Owner_UserId(ownerId);
    }
    
    public List<BookingRequest> getByStatus(String status) {
        return bookingRequestRepository.findByStatus(status);
    }
    
    public BookingRequest approveBooking(Long id) {
        return updateBookingRequestStatus(id, "APPROVED");
    }
    
    public BookingRequest rejectBooking(Long id) {
        return updateBookingRequestStatus(id, "REJECTED");
    }
}