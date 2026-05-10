package com.homeseek26.homeseeksystem.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "reviews")
public class Review {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reviewid")
    private Long reviewID;
    
    @Column(name = "rating", nullable = false)
    private Integer rating;
    
    @Column(name = "comment", length = 1000)
    private String comment;
    
    @Column(name = "review_date", nullable = false)
    private LocalDate reviewDate;
    
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;
    
    @ManyToOne
    @JoinColumn(name = "property_id")
    private Property property;
    
    @ManyToOne
    @JoinColumn(name = "boarding_id")
    private BoardingHouse boardingHouse;
    
    // Constructors
    public Review() {}
    
    // Getters and Setters
    public Long getReviewID() { return reviewID; }
    public void setReviewID(Long reviewID) { this.reviewID = reviewID; }
    
    public Integer getRating() { return rating; }
    public void setRating(Integer rating) { this.rating = rating; }
    
    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }
    
    public LocalDate getReviewDate() { return reviewDate; }
    public void setReviewDate(LocalDate reviewDate) { this.reviewDate = reviewDate; }
    
    public UserEntity getUser() { return user; }
    public void setUser(UserEntity user) { this.user = user; }
    
    public Property getProperty() { return property; }
    public void setProperty(Property property) { this.property = property; }
    
    public BoardingHouse getBoardingHouse() { return boardingHouse; }
    public void setBoardingHouse(BoardingHouse boardingHouse) { this.boardingHouse = boardingHouse; }
}