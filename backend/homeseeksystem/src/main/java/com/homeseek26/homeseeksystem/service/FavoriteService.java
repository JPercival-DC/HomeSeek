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
        // Check if already exists for property
        if (favorite.getProperty() != null && favorite.getProperty().getPropertyId() != null) {
            if (favoriteRepository.existsByUser_UserIdAndProperty_PropertyId(
                    favorite.getUser().getUserId(), 
                    favorite.getProperty().getPropertyId())) {
                throw new RuntimeException("Property already in favorites");
            }
        }
        
        // Check if already exists for boarding house
        if (favorite.getBoardingHouse() != null && favorite.getBoardingHouse().getBoardingID() != null) {
            if (favoriteRepository.existsByUser_UserIdAndBoardingHouse_BoardingID(
                    favorite.getUser().getUserId(), 
                    favorite.getBoardingHouse().getBoardingID())) {
                throw new RuntimeException("Boarding house already in favorites");
            }
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
    
    public List<Favorite> getFavoritesByProperty(Long propertyId) {
        return favoriteRepository.findByProperty_PropertyId(propertyId);
    }
    
    public boolean isFavorite(Long userId, Long boardingId) {
        return favoriteRepository.existsByUser_UserIdAndBoardingHouse_BoardingID(userId, boardingId);
    }
    
    public boolean isFavoriteByProperty(Long userId, Long propertyId) {
        if (propertyId == null) return false;
        return favoriteRepository.existsByUser_UserIdAndProperty_PropertyId(userId, propertyId);
    }
    
    public boolean isFavoriteByBoardingHouse(Long userId, Long boardingId) {
        if (boardingId == null) return false;
        return favoriteRepository.existsByUser_UserIdAndBoardingHouse_BoardingID(userId, boardingId);
    }
    
    public void removeFromFavoritesByUserAndProperty(Long userId, Long propertyId) {
        if (propertyId != null) {
            favoriteRepository.deleteByUser_UserIdAndProperty_PropertyId(userId, propertyId);
        }
    }
    
    public void removeFromFavoritesByUserAndBoardingHouse(Long userId, Long boardingId) {
        if (boardingId != null) {
            favoriteRepository.deleteByUser_UserIdAndBoardingHouse_BoardingID(userId, boardingId);
        }
    }
    
    public int getFavoriteCountByProperty(Long propertyId) {
        if (propertyId == null) return 0;
        return favoriteRepository.countByProperty_PropertyId(propertyId);
    }

}