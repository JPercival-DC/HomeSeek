package com.homeseek26.homeseeksystem.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "photos")
public class Photo {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long photoID;
    
    @Column(nullable = false)
    private String photoURL;
    
    @ManyToOne
    @JoinColumn(name = "boarding_id")
    private BoardingHouse boardingHouse;
    
    // Constructors
    public Photo() {}
    
    // Getters and Setters
    public Long getPhotoID() { return photoID; }
    public void setPhotoID(Long photoID) { this.photoID = photoID; }
    
    public String getPhotoURL() { return photoURL; }
    public void setPhotoURL(String photoURL) { this.photoURL = photoURL; }
    
    public BoardingHouse getBoardingHouse() { return boardingHouse; }
    public void setBoardingHouse(BoardingHouse boardingHouse) { this.boardingHouse = boardingHouse; }
}