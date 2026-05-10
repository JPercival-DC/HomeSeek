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
        if (bookingRequest.getRequestDate() == null) {
            bookingRequest.setRequestDate(LocalDateTime.now());
        }
        if (bookingRequest.getStatus() == null) {
            bookingRequest.setStatus("PENDING");
        }
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
        try {
            System.out.println("Service: Getting bookings for tenant: " + tenantId);
            List<BookingRequest> bookings = bookingRequestRepository.findByTenant_UserId(tenantId);
            System.out.println("Service: Found " + bookings.size() + " bookings");
            return bookings;
        } catch (Exception e) {
            System.err.println("Service error: " + e.getMessage());
            return List.of();
        }
    }
    
    public List<BookingRequest> getByProperty(Long propertyId) {
        return bookingRequestRepository.findByProperty_PropertyId(propertyId);
    }
    
    public List<BookingRequest> getByStatus(String status) {
        return bookingRequestRepository.findByStatus(status);
    }


}