package com.homeseek26.homeseeksystem.service;

import com.homeseek26.homeseeksystem.entity.Amenity;
import com.homeseek26.homeseeksystem.repository.AmenityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AmenityService {
    
    @Autowired
    private AmenityRepository amenityRepository;
    
    public Amenity createAmenity(Amenity amenity) {
        if (amenityRepository.existsByAmenityName(amenity.getAmenityName())) {
            throw new RuntimeException("Amenity already exists: " + amenity.getAmenityName());
        }
        return amenityRepository.save(amenity);
    }
    
    public Amenity getAmenityById(Long id) {
        return amenityRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Amenity not found with id: " + id));
    }
    
    public List<Amenity> getAllAmenities() {
        return amenityRepository.findAll();
    }
    
    public Amenity updateAmenity(Long id, Amenity updatedAmenity) {
        Amenity existing = getAmenityById(id);
        existing.setAmenityName(updatedAmenity.getAmenityName());
        return amenityRepository.save(existing);
    }
    
    public void deleteAmenity(Long id) {
        amenityRepository.deleteById(id);
    }
    
    public Amenity getByAmenityName(String name) {
        return amenityRepository.findByAmenityName(name)
            .orElseThrow(() -> new RuntimeException("Amenity not found: " + name));
    }
}