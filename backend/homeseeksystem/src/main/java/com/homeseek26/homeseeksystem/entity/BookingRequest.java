package com.homeseek26.homeseeksystem.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "booking_requests")
public class BookingRequest {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bookingid")
    private Long bookingID;
    
    @Column(name = "request_date", nullable = false)
    private LocalDateTime requestDate;
    
    @Column(nullable = false)
    private String status;
    
    @Column(length = 500)
    private String message;
    
    @ManyToOne
    @JoinColumn(name = "tenant_id", nullable = false)
    private UserEntity tenant;
    
    @ManyToOne
    @JoinColumn(name = "property_id")
    private Property property;
    
    @ManyToOne
    @JoinColumn(name = "boarding_id")
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
    
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    
    public UserEntity getTenant() { return tenant; }
    public void setTenant(UserEntity tenant) { this.tenant = tenant; }
    
    public Property getProperty() { return property; }
    public void setProperty(Property property) { this.property = property; }
    
    public BoardingHouse getBoardingHouse() { return boardingHouse; }
    public void setBoardingHouse(BoardingHouse boardingHouse) { this.boardingHouse = boardingHouse; }
}