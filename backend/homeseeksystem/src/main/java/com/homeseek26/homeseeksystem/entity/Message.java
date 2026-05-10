package com.homeseek26.homeseeksystem.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "messages")
public class Message {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long messageID;
    
    @Column(nullable = false, length = 1000)
    private String messageText;
    
    @Column(nullable = false)
    private LocalDateTime timestamp;
    
    @Column(nullable = false)
    private String status = "UNREAD"; // READ or UNREAD
    
    @ManyToOne
    @JoinColumn(name = "sender_id", nullable = false)
    private UserEntity sender;
    
    @ManyToOne
    @JoinColumn(name = "receiver_id", nullable = false)
    private UserEntity receiver;
    
    // Constructors
    public Message() {}
    
    // Getters and Setters
    public Long getMessageID() { return messageID; }
    public void setMessageID(Long messageID) { this.messageID = messageID; }
    
    public String getMessageText() { return messageText; }
    public void setMessageText(String messageText) { this.messageText = messageText; }
    
    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
    public UserEntity getSender() { return sender; }
    public void setSender(UserEntity sender) { this.sender = sender; }
    
    public UserEntity getReceiver() { return receiver; }
    public void setReceiver(UserEntity receiver) { this.receiver = receiver; }
}