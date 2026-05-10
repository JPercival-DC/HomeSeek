package com.homeseek26.homeseeksystem.service;

import com.homeseek26.homeseeksystem.entity.BoardingHouse;
import com.homeseek26.homeseeksystem.repository.BoardingHouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class BoardingHouseService {
    
    @Autowired
    private BoardingHouseRepository boardingHouseRepository;
    
    public BoardingHouse createBoardingHouse(BoardingHouse boardingHouse) {
        return boardingHouseRepository.save(boardingHouse);
    }
    
    public BoardingHouse getBoardingHouseById(Long id) {
        return boardingHouseRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Boarding house not found with id: " + id));
    }
    
    public List<BoardingHouse> getAllBoardingHouses() {
        return boardingHouseRepository.findAll();
    }
    
    public BoardingHouse updateBoardingHouse(Long id, BoardingHouse updatedHouse) {
        BoardingHouse existing = getBoardingHouseById(id);
        
        existing.setName(updatedHouse.getName());
        existing.setAddress(updatedHouse.getAddress());
        existing.setLocation(updatedHouse.getLocation());
        existing.setPrice(updatedHouse.getPrice());
        existing.setDescription(updatedHouse.getDescription());
        existing.setRules(updatedHouse.getRules());
        existing.setAvailabilityStatus(updatedHouse.getAvailabilityStatus());
        
        return boardingHouseRepository.save(existing);
    }
    
    public void deleteBoardingHouse(Long id) {
        boardingHouseRepository.deleteById(id);
    }
    
    public List<BoardingHouse> getByLocation(String location) {
        return boardingHouseRepository.findByLocation(location);
    }
    
    public List<BoardingHouse> getByAvailability(String status) {
        return boardingHouseRepository.findByAvailabilityStatus(status);
    }
    
    public List<BoardingHouse> getByPriceRange(Double min, Double max) {
        return boardingHouseRepository.findByPriceBetween(min, max);
    }
    
    public List<BoardingHouse> getByOwner(Long ownerId) {
        return boardingHouseRepository.findByOwner_UserId(ownerId);
    }
    
    public List<BoardingHouse> search(String keyword) {
        return boardingHouseRepository.searchByKeyword(keyword);
    }
    
    public List<BoardingHouse> getByAmenity(String amenityName) {
        return boardingHouseRepository.findByAmenityName(amenityName);
    }
}