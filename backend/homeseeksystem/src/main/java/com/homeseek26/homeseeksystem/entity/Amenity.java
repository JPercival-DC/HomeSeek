package com.homeseek26.homeseeksystem.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "amenities")
public class Amenity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long amenityID;
    
    @Column(nullable = false, unique = true)
    private String amenityName;
    
    @ManyToMany(mappedBy = "amenities")
    private List<BoardingHouse> boardingHouses;
    
    // Constructors
    public Amenity() {}
    
    // Getters and Setters
    public Long getAmenityID() { return amenityID; }
    public void setAmenityID(Long amenityID) { this.amenityID = amenityID; }
    
    public String getAmenityName() { return amenityName; }
    public void setAmenityName(String amenityName) { this.amenityName = amenityName; }
    
    public List<BoardingHouse> getBoardingHouses() { return boardingHouses; }
    public void setBoardingHouses(List<BoardingHouse> boardingHouses) { this.boardingHouses = boardingHouses; }
}