package com.homeseek26.homeseeksystem.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "booking_requests")
public class BookingRequest {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookingID;
    
    @Column(nullable = false)
    private LocalDateTime requestDate;
    
    @Column(nullable = false)
    private String status; // PENDING, APPROVED, REJECTED, CANCELLED
    
    @ManyToOne
    @JoinColumn(name = "tenant_id", nullable = false)
    private UserEntity tenant;
    
    @ManyToOne
    @JoinColumn(name = "boarding_id", nullable = false)
    private BoardingHouse boardingHouse;
    
    // Constructors
    public BookingRequest() {}
    
    // Getters and Setters
    public Long getBookingID() { return bookingID; }
    public void setBookingID(Long bookingID) { this.bookingID = bookingID; }
    
    public LocalDateTime getRequestDate() { return requestDate; }
    public void setRequestDate(LocalDateTime requestDate) { this.requestDate = requestDate; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
    public UserEntity getTenant() { return tenant; }
    public void setTenant(UserEntity tenant) { this.tenant = tenant; }
    
    public BoardingHouse getBoardingHouse() { return boardingHouse; }
    public void setBoardingHouse(BoardingHouse boardingHouse) { this.boardingHouse = boardingHouse; }
}