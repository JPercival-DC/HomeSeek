package com.homeseek26.homeseeksystem.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "boarding_houses")
public class BoardingHouse {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long boardingID;
    
    @Column(nullable = false)
    private String name;
    
    @Column(nullable = false)
    private String address;
    
    @Column(nullable = false)
    private String location;
    
    @Column(nullable = false)
    private Double price;
    
    @Column(length = 1000)
    private String description;
    
    private String rules;
    
    @Column(name = "availability_status")
    private String availabilityStatus;
    
    @ManyToOne
    @JoinColumn(name = "owner_id")
    private UserEntity owner;
    
    @OneToMany(mappedBy = "boardingHouse", cascade = CascadeType.ALL)
    private List<Photo> photos;
    
    @ManyToMany
    @JoinTable(
        name = "boarding_house_amenities",
        joinColumns = @JoinColumn(name = "boarding_id"),
        inverseJoinColumns = @JoinColumn(name = "amenity_id")
    )
    private List<Amenity> amenities;
    
    @OneToMany(mappedBy = "boardingHouse")
    private List<Review> reviews;
    
    @OneToMany(mappedBy = "boardingHouse")
    private List<BookingRequest> bookingRequests;
    
    @OneToMany(mappedBy = "boardingHouse")
    private List<Favorite> favorites;
    
    // Constructors
    public BoardingHouse() {}
    
    // Getters and Setters
    public Long getBoardingID() { return boardingID; }
    public void setBoardingID(Long boardingID) { this.boardingID = boardingID; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    
    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public String getRules() { return rules; }
    public void setRules(String rules) { this.rules = rules; }
    
    public String getAvailabilityStatus() { return availabilityStatus; }
    public void setAvailabilityStatus(String availabilityStatus) { this.availabilityStatus = availabilityStatus; }
    
    public UserEntity getOwner() { return owner; }
    public void setOwner(UserEntity owner) { this.owner = owner; }
    
    public List<Photo> getPhotos() { return photos; }
    public void setPhotos(List<Photo> photos) { this.photos = photos; }
    
    public List<Amenity> getAmenities() { return amenities; }
    public void setAmenities(List<Amenity> amenities) { this.amenities = amenities; }
    
    public List<Review> getReviews() { return reviews; }
    public void setReviews(List<Review> reviews) { this.reviews = reviews; }
    
    public List<BookingRequest> getBookingRequests() { return bookingRequests; }
    public void setBookingRequests(List<BookingRequest> bookingRequests) { this.bookingRequests = bookingRequests; }
    
    public List<Favorite> getFavorites() { return favorites; }
    public void setFavorites(List<Favorite> favorites) { this.favorites = favorites; }
}