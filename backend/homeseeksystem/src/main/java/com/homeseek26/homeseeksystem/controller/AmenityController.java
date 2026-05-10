package com.homeseek26.homeseeksystem.controller;

import com.homeseek26.homeseeksystem.entity.Amenity;
import com.homeseek26.homeseeksystem.service.AmenityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/amenities")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:5173"})
public class AmenityController {
    
    @Autowired
    private AmenityService amenityService;
    
    @PostMapping
    public Amenity createAmenity(@RequestBody Amenity amenity) {
        return amenityService.createAmenity(amenity);
    }
    
    @GetMapping("/{id}")
    public Amenity getAmenityById(@PathVariable Long id) {
        return amenityService.getAmenityById(id);
    }
    
    @GetMapping
    public List<Amenity> getAllAmenities() {
        return amenityService.getAllAmenities();
    }
    
    @PutMapping("/{id}")
    public Amenity updateAmenity(@PathVariable Long id, @RequestBody Amenity amenity) {
        return amenityService.updateAmenity(id, amenity);
    }
    
    @DeleteMapping("/{id}")
    public String deleteAmenity(@PathVariable Long id) {
        amenityService.deleteAmenity(id);
        return "Amenity deleted successfully";
    }
    
    @GetMapping("/name/{name}")
    public Amenity getByAmenityName(@PathVariable String name) {
        return amenityService.getByAmenityName(name);
    }
}