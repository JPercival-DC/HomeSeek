package com.homeseek26.homeseeksystem.controller;

import com.homeseek26.homeseeksystem.entity.BookingRequest;
import com.homeseek26.homeseeksystem.entity.UserEntity;
import com.homeseek26.homeseeksystem.entity.Property;
import com.homeseek26.homeseeksystem.service.BookingRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/bookings")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:5173"})
public class BookingRequestController {
    
    @Autowired
    private BookingRequestService bookingRequestService;
    
    // Accept both JSON body and query parameters
    @PostMapping
    public ResponseEntity<?> createBookingRequest(
            @RequestParam(required = false) Long tenantId,
            @RequestParam(required = false) Long propertyId,
            @RequestParam(required = false) String message,
            @RequestBody(required = false) Map<String, Object> requestBody) {
        try {
            Long finalTenantId = tenantId;
            Long finalPropertyId = propertyId;
            String finalMessage = message;
            
            // If query params are not provided, try to get from body
            if (finalTenantId == null && requestBody != null) {
                if (requestBody.containsKey("tenantId")) {
                    finalTenantId = Long.valueOf(requestBody.get("tenantId").toString());
                } else if (requestBody.containsKey("tenant") && requestBody.get("tenant") instanceof Map) {
                    Map<String, Object> tenant = (Map<String, Object>) requestBody.get("tenant");
                    finalTenantId = Long.valueOf(tenant.get("userId").toString());
                }
            }
            
            if (finalPropertyId == null && requestBody != null) {
                if (requestBody.containsKey("propertyId")) {
                    finalPropertyId = Long.valueOf(requestBody.get("propertyId").toString());
                } else if (requestBody.containsKey("property") && requestBody.get("property") instanceof Map) {
                    Map<String, Object> property = (Map<String, Object>) requestBody.get("property");
                    finalPropertyId = Long.valueOf(property.get("propertyId").toString());
                }
            }
            
            if (finalMessage == null && requestBody != null && requestBody.containsKey("message")) {
                finalMessage = requestBody.get("message").toString();
            }
            
            if (finalTenantId == null || finalPropertyId == null) {
                Map<String, String> error = new HashMap<>();
                error.put("error", "tenantId and propertyId are required");
                return ResponseEntity.badRequest().body(error);
            }
            
            System.out.println("Creating booking - tenantId: " + finalTenantId + ", propertyId: " + finalPropertyId);
            
            BookingRequest booking = new BookingRequest();
            
            UserEntity tenant = new UserEntity();
            tenant.setUserId(finalTenantId);
            booking.setTenant(tenant);
            
            Property property = new Property();
            property.setPropertyId(finalPropertyId);
            booking.setProperty(property);
            
            booking.setRequestDate(LocalDateTime.now());
            booking.setStatus("PENDING");
            booking.setMessage(finalMessage);
            
            BookingRequest saved = bookingRequestService.createBookingRequest(booking);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Booking request sent successfully");
            response.put("bookingId", saved.getBookingID());
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            e.printStackTrace();
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }
    
    // GET BOOKINGS BY TENANT ID - ADD THIS ENDPOINT
    @GetMapping("/tenant/{tenantId}")
    public ResponseEntity<?> getBookingsByTenant(@PathVariable Long tenantId) {
        try {
            System.out.println("=== GETTING BOOKINGS FOR TENANT: " + tenantId);
            List<BookingRequest> bookings = bookingRequestService.getByTenant(tenantId);
            System.out.println("Found " + bookings.size() + " bookings");
            
            // Log each booking for debugging
            for (BookingRequest booking : bookings) {
                System.out.println("Booking ID: " + booking.getBookingID() + 
                                   ", Property ID: " + (booking.getProperty() != null ? booking.getProperty().getPropertyId() : "null") +
                                   ", Status: " + booking.getStatus());
            }
            
            return ResponseEntity.ok(bookings);
        } catch (Exception e) {
            System.err.println("Error fetching bookings: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.ok(List.of());
        }
    }
    
    @PutMapping("/{id}/cancel")
    public ResponseEntity<?> cancelBooking(@PathVariable Long id) {
        try {
            BookingRequest booking = bookingRequestService.updateBookingRequestStatus(id, "CANCELLED");
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Booking cancelled successfully");
            response.put("booking", booking);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}