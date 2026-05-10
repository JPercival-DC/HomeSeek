package com.homeseek26.homeseeksystem.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "notifications")
public class Notification {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long notificationID;
    
    @Column(nullable = false)
    private String message;
    
    @Column(nullable = false)
    private LocalDateTime dateSent;
    
    @Column(nullable = false)
    private String status; // READ, UNREAD
    
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;
    
    // Constructors
    public Notification() {}
    
    // Getters and Setters
    public Long getNotificationID() { return notificationID; }
    public void setNotificationID(Long notificationID) { this.notificationID = notificationID; }
    
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    
    public LocalDateTime getDateSent() { return dateSent; }
    public void setDateSent(LocalDateTime dateSent) { this.dateSent = dateSent; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
    public UserEntity getUser() { return user; }
    public void setUser(UserEntity user) { this.user = user; }
}