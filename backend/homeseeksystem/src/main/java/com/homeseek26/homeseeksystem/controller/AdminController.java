package com.homeseek26.homeseeksystem.controller;

import com.homeseek26.homeseeksystem.entity.UserEntity;
import com.homeseek26.homeseeksystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:5173"})
public class AdminController {

    @Autowired
    private UserService userService;

    // CREATE ADMIN
    @PostMapping("/create")
    public ResponseEntity<?> createAdmin(@RequestBody UserEntity user) {
        try {
            user.setRole("admin"); // Changed to lowercase "admin"
            UserEntity createdAdmin = userService.registerUser(user);
            return new ResponseEntity<>(createdAdmin, HttpStatus.CREATED);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
    }

    // READ ALL ADMINS
    @GetMapping("/all")
    public ResponseEntity<List<UserEntity>> getAllAdmins() {
        List<UserEntity> admins = userService.getAllAdmins();
        return new ResponseEntity<>(admins, HttpStatus.OK);
    }

    // READ ADMIN BY ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getAdminById(@PathVariable Long id) {
        try {
            UserEntity admin = userService.getUserById(id);
            if (!"admin".equals(admin.getRole())) {
                return new ResponseEntity<>("User is not an admin", HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(admin, HttpStatus.OK);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
    }

    // UPDATE ADMIN
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateAdmin(@PathVariable Long id, @RequestBody UserEntity user) {
        try {
            UserEntity existingAdmin = userService.getUserById(id);
            if (!"admin".equals(existingAdmin.getRole())) {
                return new ResponseEntity<>("User is not an admin", HttpStatus.BAD_REQUEST);
            }
            user.setRole("admin");
            UserEntity updatedAdmin = userService.updateUser(id, user);
            return new ResponseEntity<>(updatedAdmin, HttpStatus.OK);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
    }

    // DELETE ADMIN
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteAdmin(@PathVariable Long id) {
        try {
            UserEntity admin = userService.getUserById(id);
            if (!"admin".equals(admin.getRole())) {
                return new ResponseEntity<>("User is not an admin", HttpStatus.BAD_REQUEST);
            }
            userService.deleteUser(id);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Admin deleted successfully");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
    }

    // GET ALL TENANTS (users with role "tenant")
    @GetMapping("/tenants")
    public ResponseEntity<List<UserEntity>> getAllTenants() {
        List<UserEntity> tenants = userService.getUsersByRole("tenant");
        return new ResponseEntity<>(tenants, HttpStatus.OK);
    }

    // GET ALL OWNERS
    @GetMapping("/owners")
    public ResponseEntity<List<UserEntity>> getAllOwners() {
        List<UserEntity> owners = userService.getUsersByRole("owner");
        return new ResponseEntity<>(owners, HttpStatus.OK);
    }

    // GET STATISTICS
    @GetMapping("/statistics")
    public ResponseEntity<Map<String, Long>> getStatistics() {
        Map<String, Long> stats = userService.getUserStatistics();
        return new ResponseEntity<>(stats, HttpStatus.OK);
    }
}