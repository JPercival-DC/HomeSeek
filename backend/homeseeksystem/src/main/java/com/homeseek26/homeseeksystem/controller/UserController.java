package com.homeseek26.homeseeksystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.homeseek26.homeseeksystem.entity.UserEntity;
import com.homeseek26.homeseeksystem.service.UserService;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = {
    "http://localhost:3000",
    "http://localhost:5173"
})
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public UserEntity register(@RequestBody UserEntity user) {
        return userService.registerUser(user);
    }

    @PostMapping("/login")
        public UserEntity login(@RequestBody UserEntity user) {
    return userService.loginUser(user.getEmail(), user.getPassword());
}
}