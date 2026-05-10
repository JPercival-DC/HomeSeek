package com.homeseek26.homeseeksystem.service;

import java.util.Optional;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.homeseek26.homeseeksystem.entity.UserEntity;
import com.homeseek26.homeseeksystem.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // REGISTER
    public UserEntity registerUser(UserEntity user) {
        Optional<UserEntity> existingUser = userRepository.findByEmail(user.getEmail());

        if (existingUser.isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        // Set default role to "tenant" if not specified
        if (user.getRole() == null || user.getRole().isEmpty()) {
            user.setRole("tenant");
        }

        return userRepository.save(user);
    }

    // LOGIN
    public UserEntity loginUser(String email, String password) {
        System.out.println("LOGIN ATTEMPT: " + email + " / " + password);

        Optional<UserEntity> userOpt = userRepository.findByEmail(email);

        if (userOpt.isPresent()) {
            UserEntity user = userOpt.get();

            if (user.getPassword().equals(password)) {
                return user;
            }

            throw new RuntimeException("Wrong password");
        }

        throw new RuntimeException("User not found");
    }

    // GET USER BY ID
    public UserEntity getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }

    // GET ALL USERS
    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }

    // GET ALL ADMINS
    public List<UserEntity> getAllAdmins() {
        return userRepository.findByRole("admin");
    }

    // GET USERS BY ROLE
    public List<UserEntity> getUsersByRole(String role) {
        return userRepository.findByRole(role);
    }

    // UPDATE USER
    public UserEntity updateUser(Long id, UserEntity newUser) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (newUser.getName() != null) user.setName(newUser.getName());
        if (newUser.getEmail() != null) user.setEmail(newUser.getEmail());
        if (newUser.getPassword() != null) user.setPassword(newUser.getPassword());
        if (newUser.getPhone() != null) user.setPhone(newUser.getPhone());
        if (newUser.getRole() != null) user.setRole(newUser.getRole());

        return userRepository.save(user);
    }

    // DELETE USER
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User not found");
        }
        userRepository.deleteById(id);
    }

    // GET USER STATISTICS
    public Map<String, Long> getUserStatistics() {
        Map<String, Long> stats = new HashMap<>();
        stats.put("totalUsers", (long) userRepository.findAll().size());
        stats.put("totalAdmins", (long) userRepository.findByRole("admin").size());
        stats.put("totalOwners", (long) userRepository.findByRole("owner").size());
        stats.put("totalTenants", (long) userRepository.findByRole("tenant").size());
        return stats;
    }
}