package com.homeseek26.homeseeksystem.service;

import com.homeseek26.homeseeksystem.entity.Favorite;
import com.homeseek26.homeseeksystem.repository.FavoriteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class FavoriteService {
    
    @Autowired
    private FavoriteRepository favoriteRepository;
    
    public Favorite addToFavorites(Favorite favorite) {
        if (favoriteRepository.existsByUser_UserIdAndBoardingHouse_BoardingID(
                favorite.getUser().getUserId(), 
                favorite.getBoardingHouse().getBoardingID())) {
            throw new RuntimeException("Property already in favorites");
        }
        favorite.setDateSaved(LocalDateTime.now());
        return favoriteRepository.save(favorite);
    }
    
    public Favorite getFavoriteById(Long id) {
        return favoriteRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Favorite not found with id: " + id));
    }
    
    public List<Favorite> getAllFavorites() {
        return favoriteRepository.findAll();
    }
    
    public void removeFromFavorites(Long id) {
        favoriteRepository.deleteById(id);
    }
    
    public List<Favorite> getFavoritesByUser(Long userId) {
        return favoriteRepository.findByUser_UserId(userId);
    }
    
    public List<Favorite> getFavoritesByBoardingHouse(Long boardingId) {
        return favoriteRepository.findByBoardingHouse_BoardingID(boardingId);
    }
    
    public boolean isFavorite(Long userId, Long boardingId) {
        return favoriteRepository.existsByUser_UserIdAndBoardingHouse_BoardingID(userId, boardingId);
    }
    
    public void removeFromFavoritesByUserAndProperty(Long userId, Long boardingId) {
        favoriteRepository.deleteByUser_UserIdAndBoardingHouse_BoardingID(userId, boardingId);
    }
}