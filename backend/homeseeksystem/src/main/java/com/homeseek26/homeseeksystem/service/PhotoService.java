package com.homeseek26.homeseeksystem.service;

import com.homeseek26.homeseeksystem.entity.Photo;
import com.homeseek26.homeseeksystem.repository.PhotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PhotoService {
    
    @Autowired
    private PhotoRepository photoRepository;
    
    public Photo addPhoto(Photo photo) {
        return photoRepository.save(photo);
    }
    
    public Photo getPhotoById(Long id) {
        return photoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Photo not found with id: " + id));
    }
    
    public List<Photo> getAllPhotos() {
        return photoRepository.findAll();
    }
    
    public List<Photo> getPhotosByBoardingHouse(Long boardingId) {
        return photoRepository.findByBoardingHouse_BoardingID(boardingId);
    }
    
    public void deletePhoto(Long id) {
        photoRepository.deleteById(id);
    }
    
    public void deletePhotosByBoardingHouse(Long boardingId) {
        photoRepository.deleteByBoardingHouse_BoardingID(boardingId);
    }
}   