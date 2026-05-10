package com.homeseek26.homeseeksystem.controller;

import com.homeseek26.homeseeksystem.entity.Favorite;
import com.homeseek26.homeseeksystem.entity.UserEntity;
import com.homeseek26.homeseeksystem.entity.Property;
import com.homeseek26.homeseeksystem.service.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/favorites")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:5173"})
public class FavoriteController {
    
    @Autowired
    private FavoriteService favoriteService;
    
    // ADD TO FAVORITES - Using query parameters (simpler)
    @PostMapping
    public ResponseEntity<?> addToFavorites(
            @RequestParam Long userId, 
            @RequestParam Long propertyId) {
        try {
            System.out.println("=== ADDING FAVORITE ===");
            System.out.println("User ID: " + userId);
            System.out.println("Property ID: " + propertyId);
            
            // Create favorite object
            Favorite favorite = new Favorite();
            
            UserEntity user = new UserEntity();
            user.setUserId(userId);
            favorite.setUser(user);
            
            Property property = new Property();
            property.setPropertyId(propertyId);
            favorite.setProperty(property);
            
            favorite.setDateSaved(LocalDateTime.now());
            
            // Save to database
            Favorite saved = favoriteService.addToFavorites(favorite);
            
            // Return success response
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Added to favorites successfully");
            response.put("favoriteId", saved.getFavoriteID());
            
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
            
        } catch (Exception e) {
            e.printStackTrace();
            Map<String, String> error = new HashMap<>();
            error.put("success", "false");
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }
    
    // CHECK IF PROPERTY IS FAVORITED
    @GetMapping("/check")
    public ResponseEntity<?> checkFavorite(
            @RequestParam Long userId, 
            @RequestParam Long propertyId) {
        try {
            boolean isFavorite = favoriteService.isFavoriteByProperty(userId, propertyId);
            System.out.println("Check favorite - User: " + userId + ", Property: " + propertyId + ", Result: " + isFavorite);
            return ResponseEntity.ok(isFavorite);
        } catch (Exception e) {
            System.out.println("Error checking favorite: " + e.getMessage());
            return ResponseEntity.ok(false);
        }
    }
    
    // REMOVE FAVORITE
    @DeleteMapping("/remove")
    public ResponseEntity<?> removeFavorite(
            @RequestParam Long userId, 
            @RequestParam Long propertyId) {
        try {
            favoriteService.removeFromFavoritesByUserAndProperty(userId, propertyId);
            Map<String, String> response = new HashMap<>();
            response.put("success", "true");
            response.put("message", "Removed from favorites");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("success", "false");
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }
    
    // GET ALL FAVORITES FOR A USER
    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getUserFavorites(@PathVariable Long userId) {
        try {
            List<Favorite> favorites = favoriteService.getFavoritesByUser(userId);
            return ResponseEntity.ok(favorites);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
    
    // GET ALL FAVORITES FOR A PROPERTY
    @GetMapping("/property/{propertyId}")
    public ResponseEntity<?> getPropertyFavorites(@PathVariable Long propertyId) {
        try {
            List<Favorite> favorites = favoriteService.getFavoritesByProperty(propertyId);
            return ResponseEntity.ok(favorites);
        } catch (Exception e) {
            return ResponseEntity.ok(List.of());
        }
    }
}