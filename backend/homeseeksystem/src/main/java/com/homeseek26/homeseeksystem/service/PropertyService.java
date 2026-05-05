package com.homeseek26.homeseeksystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.homeseek26.homeseeksystem.entity.Property;
import com.homeseek26.homeseeksystem.repository.PropertyRepository;

import java.util.List;

@Service
public class PropertyService {
    @Autowired
    PropertyRepository repository;

    public PropertyService() {}

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

}
