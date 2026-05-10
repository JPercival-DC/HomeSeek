package com.homeseek26.homeseeksystem.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "favorites")
public class Favorite {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long favoriteID;
    
    @Column(nullable = false)
    private LocalDateTime dateSaved;
    
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;
    
    @ManyToOne
    @JoinColumn(name = "boarding_id", nullable = false)
    private BoardingHouse boardingHouse;
    
    // Constructors
    public Favorite() {}
    
    // Getters and Setters
    public Long getFavoriteID() { return favoriteID; }
    public void setFavoriteID(Long favoriteID) { this.favoriteID = favoriteID; }
    
    public LocalDateTime getDateSaved() { return dateSaved; }
    public void setDateSaved(LocalDateTime dateSaved) { this.dateSaved = dateSaved; }
    
    public UserEntity getUser() { return user; }
    public void setUser(UserEntity user) { this.user = user; }
    
    public BoardingHouse getBoardingHouse() { return boardingHouse; }
    public void setBoardingHouse(BoardingHouse boardingHouse) { this.boardingHouse = boardingHouse; }
}