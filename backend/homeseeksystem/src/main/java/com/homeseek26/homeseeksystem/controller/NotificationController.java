package com.homeseek26.homeseeksystem.controller;

import com.homeseek26.homeseeksystem.entity.Notification;
import com.homeseek26.homeseeksystem.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/notifications")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:5173"})
public class NotificationController {
    
    @Autowired
    private NotificationService notificationService;
    
    @PostMapping
    public Notification createNotification(@RequestBody Notification notification) {
        return notificationService.createNotification(notification);
    }
    
    @GetMapping("/{id}")
    public Notification getNotificationById(@PathVariable Long id) {
        return notificationService.getNotificationById(id);
    }
    
    @GetMapping
    public List<Notification> getAllNotifications() {
        return notificationService.getAllNotifications();
    }
    
    @PutMapping("/{id}/status")
    public Notification updateNotificationStatus(@PathVariable Long id, @RequestParam String status) {
        return notificationService.updateNotificationStatus(id, status);
    }
    
    @DeleteMapping("/{id}")
    public String deleteNotification(@PathVariable Long id) {
        notificationService.deleteNotification(id);
        return "Notification deleted successfully";
    }
    
    @GetMapping("/user/{userId}")
    public List<Notification> getNotificationsByUser(@PathVariable Long userId) {
        return notificationService.getNotificationsByUser(userId);
    }
    
    @GetMapping("/user/{userId}/unread")
    public List<Notification> getUnreadNotifications(@PathVariable Long userId) {
        return notificationService.getUnreadNotificationsByUser(userId);
    }
    
    @PutMapping("/{id}/read")
    public Notification markAsRead(@PathVariable Long id) {
        return notificationService.markAsRead(id);
    }
    
    @GetMapping("/user/{userId}/unread-count")
    public int getUnreadCount(@PathVariable Long userId) {
        return notificationService.getUnreadCount(userId);
    }
}