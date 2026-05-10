package com.homeseek26.homeseeksystem.controller;

import com.homeseek26.homeseeksystem.entity.BookingRequest;
import com.homeseek26.homeseeksystem.service.BookingRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/bookings")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:5173"})
public class BookingRequestController {
    
    @Autowired
    private BookingRequestService bookingRequestService;
    
    @PostMapping
    public BookingRequest createBookingRequest(@RequestBody BookingRequest bookingRequest) {
        return bookingRequestService.createBookingRequest(bookingRequest);
    }
    
    @GetMapping("/{id}")
    public BookingRequest getBookingRequestById(@PathVariable Long id) {
        return bookingRequestService.getBookingRequestById(id);
    }
    
    @GetMapping
    public List<BookingRequest> getAllBookingRequests() {
        return bookingRequestService.getAllBookingRequests();
    }
    
    @PutMapping("/{id}/status")
    public BookingRequest updateBookingRequestStatus(@PathVariable Long id, @RequestParam String status) {
        return bookingRequestService.updateBookingRequestStatus(id, status);
    }
    
    @DeleteMapping("/{id}")
    public String deleteBookingRequest(@PathVariable Long id) {
        bookingRequestService.deleteBookingRequest(id);
        return "Booking request deleted successfully";
    }
    
    @GetMapping("/tenant/{tenantId}")
    public List<BookingRequest> getByTenant(@PathVariable Long tenantId) {
        return bookingRequestService.getByTenant(tenantId);
    }
    
    @GetMapping("/boarding-house/{boardingId}")
    public List<BookingRequest> getByBoardingHouse(@PathVariable Long boardingId) {
        return bookingRequestService.getByBoardingHouse(boardingId);
    }
    
    @GetMapping("/owner/{ownerId}")
    public List<BookingRequest> getByOwner(@PathVariable Long ownerId) {
        return bookingRequestService.getByOwner(ownerId);
    }
    
    @GetMapping("/status/{status}")
    public List<BookingRequest> getByStatus(@PathVariable String status) {
        return bookingRequestService.getByStatus(status);
    }
    
    @PostMapping("/{id}/approve")
    public BookingRequest approveBooking(@PathVariable Long id) {
        return bookingRequestService.approveBooking(id);
    }
    
    @PostMapping("/{id}/reject")
    public BookingRequest rejectBooking(@PathVariable Long id) {
        return bookingRequestService.rejectBooking(id);
    }
}