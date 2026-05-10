package com.homeseek26.homeseeksystem.controller;

import com.homeseek26.homeseeksystem.entity.Property;
import com.homeseek26.homeseeksystem.service.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/properties")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:5173"})
public class PropertyController {
    
    @Autowired
    private PropertyService propertyService;

    // CREATE PROPERTY - Only owners and admins
    @PostMapping
    public ResponseEntity<?> addProperty(@RequestBody Property property, 
                                        @RequestHeader(value = "X-User-Role", required = false) String userRole) {
        try {
            // Check if user is authorized (owner or admin)
            if (userRole == null || (!"owner".equals(userRole) && !"admin".equals(userRole))) {
                Map<String, String> error = new HashMap<>();
                error.put("error", "Only owners and admins can list properties");
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
            }
            
            Property newProperty = propertyService.addProperty(property);
            return ResponseEntity.status(HttpStatus.CREATED).body(newProperty);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }

    // GET PROPERTY BY ID - Anyone can access
    @GetMapping("/{id}")
    public ResponseEntity<?> getPropertyById(@PathVariable Long id) {
        try {
            Property property = propertyService.getPropertyById(id);
            if (property == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Property not found with id: " + id));
            }
            return ResponseEntity.ok(property);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("error", e.getMessage()));
        }
    }

    // GET ALL PROPERTIES - Anyone can access
    @GetMapping
    public ResponseEntity<List<Property>> getAllProperties() {
        try {
            List<Property> properties = propertyService.getAllProperties();
            return ResponseEntity.ok(properties);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // UPDATE PROPERTY - Only owner of the property or admin
    @PutMapping("/{id}")
    public ResponseEntity<?> updateProperty(@PathVariable Long id, 
                                           @RequestBody Property updatedProperty,
                                           @RequestHeader(value = "X-User-Role", required = false) String userRole,
                                           @RequestHeader(value = "X-User-Id", required = false) Long userId) {
        try {
            Property existingProperty = propertyService.getPropertyById(id);
            
            if (existingProperty == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Property not found with id: " + id));
            }
            
            // Check authorization: admin or the owner of the property
            if (userRole == null || (!"admin".equals(userRole) && !existingProperty.getOwnerId().equals(userId))) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Map.of("error", "You are not authorized to update this property"));
            }
            
            Property property = propertyService.updateProperty(id, updatedProperty);
            return ResponseEntity.ok(property);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of("error", e.getMessage()));
        }
    }

    // DELETE PROPERTY - Only admin
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProperty(@PathVariable Long id,
                                           @RequestHeader(value = "X-User-Role", required = false) String userRole) {
        try {
            // Check if user is admin
            if (userRole == null || !"admin".equals(userRole)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Map.of("error", "Only admins can delete properties"));
            }
            
            String result = propertyService.deleteProperty(id);
            return ResponseEntity.ok(Map.of("message", result));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of("error", e.getMessage()));
        }
    }
    
    // GET PROPERTIES BY OWNER - Anyone can access
    @GetMapping("/owner/{ownerId}")
    public ResponseEntity<?> getPropertiesByOwner(@PathVariable Long ownerId) {
        try {
            List<Property> properties = propertyService.getPropertiesByOwner(ownerId);
            return ResponseEntity.ok(properties);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("error", e.getMessage()));
        }
    }
    
    // GET AVAILABLE PROPERTIES - Anyone can access
    @GetMapping("/available")
    public ResponseEntity<?> getAvailableProperties() {
        try {
            List<Property> properties = propertyService.getAvailableProperties();
            return ResponseEntity.ok(properties);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("error", e.getMessage()));
        }
    }

    // GET PROPERTY FAVORITE STATUS
    @GetMapping("/{id}/favorite")
    public ResponseEntity<?> getFavoriteStatus(@PathVariable Long id, @RequestParam Long userId) {
        try {
            // This would check if user has favorited this property
            boolean isFavorite = false;
            // Implementation depends on your Favorite entity
            return ResponseEntity.ok(isFavorite);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    // ADD PROPERTY TO FAVORITES
    @PostMapping("/{id}/favorite")
    public ResponseEntity<?> addToFavorites(@PathVariable Long id, @RequestParam Long userId) {
        try {
            // Implementation to add to favorites
            return ResponseEntity.ok("Added to favorites");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}