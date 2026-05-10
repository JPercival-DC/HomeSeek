package com.homeseek26.homeseeksystem.controller;

import com.homeseek26.homeseeksystem.entity.BoardingHouse;
import com.homeseek26.homeseeksystem.service.BoardingHouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/boarding-houses")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:5173"})
public class BoardingHouseController {
    
    @Autowired
    private BoardingHouseService boardingHouseService;
    
    @PostMapping
    public BoardingHouse createBoardingHouse(@RequestBody BoardingHouse boardingHouse) {
        return boardingHouseService.createBoardingHouse(boardingHouse);
    }
    
    @GetMapping("/{id}")
    public BoardingHouse getBoardingHouseById(@PathVariable Long id) {
        return boardingHouseService.getBoardingHouseById(id);
    }
    
    @GetMapping
    public List<BoardingHouse> getAllBoardingHouses() {
        return boardingHouseService.getAllBoardingHouses();
    }
    
    @PutMapping("/{id}")
    public BoardingHouse updateBoardingHouse(@PathVariable Long id, @RequestBody BoardingHouse boardingHouse) {
        return boardingHouseService.updateBoardingHouse(id, boardingHouse);
    }
    
    @DeleteMapping("/{id}")
    public String deleteBoardingHouse(@PathVariable Long id) {
        boardingHouseService.deleteBoardingHouse(id);
        return "Boarding house deleted successfully";
    }
    
    @GetMapping("/location/{location}")
    public List<BoardingHouse> getByLocation(@PathVariable String location) {
        return boardingHouseService.getByLocation(location);
    }
    
    @GetMapping("/availability/{status}")
    public List<BoardingHouse> getByAvailability(@PathVariable String status) {
        return boardingHouseService.getByAvailability(status);
    }
    
    @GetMapping("/price-range")
    public List<BoardingHouse> getByPriceRange(@RequestParam Double min, @RequestParam Double max) {
        return boardingHouseService.getByPriceRange(min, max);
    }
    
    @GetMapping("/owner/{ownerId}")
    public List<BoardingHouse> getByOwner(@PathVariable Long ownerId) {
        return boardingHouseService.getByOwner(ownerId);
    }
    
    @GetMapping("/search")
    public List<BoardingHouse> search(@RequestParam String keyword) {
        return boardingHouseService.search(keyword);
    }
    
    @GetMapping("/amenity/{amenityName}")
    public List<BoardingHouse> getByAmenity(@PathVariable String amenityName) {
        return boardingHouseService.getByAmenity(amenityName);
    }
}