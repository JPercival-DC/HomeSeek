package com.homeseek26.homeseeksystem.controller;

import com.homeseek26.homeseeksystem.entity.Photo;
import com.homeseek26.homeseeksystem.service.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/photos")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:5173"})
public class PhotoController {
    
    @Autowired
    private PhotoService photoService;
    
    @PostMapping
    public Photo addPhoto(@RequestBody Photo photo) {
        return photoService.addPhoto(photo);
    }
    
    @GetMapping("/{id}")
    public Photo getPhotoById(@PathVariable Long id) {
        return photoService.getPhotoById(id);
    }
    
    @GetMapping
    public List<Photo> getAllPhotos() {
        return photoService.getAllPhotos();
    }
    
    @GetMapping("/boarding-house/{boardingId}")
    public List<Photo> getPhotosByBoardingHouse(@PathVariable Long boardingId) {
        return photoService.getPhotosByBoardingHouse(boardingId);
    }
    
    @DeleteMapping("/{id}")
    public String deletePhoto(@PathVariable Long id) {
        photoService.deletePhoto(id);
        return "Photo deleted successfully";
    }
}