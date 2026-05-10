package com.homeseek26.homeseeksystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.homeseek26.homeseeksystem.entity.Property;
import com.homeseek26.homeseeksystem.repository.PropertyRepository;

import java.util.List;

@Service
public class PropertyService {
    
    @Autowired
    private PropertyRepository repository;

    public Property addProperty(Property property) {
        return repository.save(property);
    }

    public Property getPropertyById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public List<Property> getAllProperties() {
        return repository.findAll();
    }

    public Property updateProperty(Long id, Property updatedProperty) {
        Property property = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Property not found"));

        property.setPropertyName(updatedProperty.getPropertyName());
        property.setAddress(updatedProperty.getAddress());
        property.setPrice(updatedProperty.getPrice());
        property.setDescription(updatedProperty.getDescription());
        property.setType(updatedProperty.getType());
        property.setRooms(updatedProperty.getRooms());
        property.setAvailabilityStatus(updatedProperty.getAvailabilityStatus());
        
        // Update ownerId if provided
        if (updatedProperty.getOwnerId() != null) {
            property.setOwnerId(updatedProperty.getOwnerId());
        }

        return repository.save(property);
    }

    public String deleteProperty(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return "Property " + id + " deleted successfully";
        } else {
            return "Property not found";
        }
    }
    
    public List<Property> getPropertiesByOwner(Long ownerId) {
        return repository.findByOwnerId(ownerId);
    }
    
    public List<Property> getAvailableProperties() {
        return repository.findByAvailabilityStatus("Available");
    }
    
    // Additional useful methods
    public List<Property> getPropertiesByType(String type) {
        return repository.findByType(type);
    }
    
    public List<Property> searchProperties(String keyword) {
        return repository.findByPropertyNameContainingIgnoreCase(keyword);
    }


    public Property updatePropertyStatus(Long id, String newStatus) {
        Property property = repository.findById(id).orElse(null);
        if (property == null) return null;
        property.setAvailabilityStatus(newStatus);
        return repository.save(property);
    }

}