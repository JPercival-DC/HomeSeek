package com.homeseek26.homeseeksystem.controller;

import com.homeseek26.homeseeksystem.entity.Favorite;
import com.homeseek26.homeseeksystem.service.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/favorites")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:5173"})
public class FavoriteController {
    
    @Autowired
    private FavoriteService favoriteService;
    
    @PostMapping
    public Favorite addToFavorites(@RequestBody Favorite favorite) {
        return favoriteService.addToFavorites(favorite);
    }
    
    @GetMapping("/{id}")
    public Favorite getFavoriteById(@PathVariable Long id) {
        return favoriteService.getFavoriteById(id);
    }
    
    @GetMapping
    public List<Favorite> getAllFavorites() {
        return favoriteService.getAllFavorites();
    }
    
    @DeleteMapping("/{id}")
    public String removeFromFavorites(@PathVariable Long id) {
        favoriteService.removeFromFavorites(id);
        return "Favorite removed successfully";
    }
    
    @GetMapping("/user/{userId}")
    public List<Favorite> getFavoritesByUser(@PathVariable Long userId) {
        return favoriteService.getFavoritesByUser(userId);
    }
    
    @GetMapping("/boarding-house/{boardingId}")
    public List<Favorite> getFavoritesByBoardingHouse(@PathVariable Long boardingId) {
        return favoriteService.getFavoritesByBoardingHouse(boardingId);
    }
    
    @GetMapping("/check")
    public boolean isFavorite(@RequestParam Long userId, @RequestParam Long boardingId) {
        return favoriteService.isFavorite(userId, boardingId);
    }
    
    @DeleteMapping("/remove")
    public String removeFavoriteByUserAndProperty(@RequestParam Long userId, @RequestParam Long boardingId) {
        favoriteService.removeFromFavoritesByUserAndProperty(userId, boardingId);
        return "Favorite removed successfully";
    }
}