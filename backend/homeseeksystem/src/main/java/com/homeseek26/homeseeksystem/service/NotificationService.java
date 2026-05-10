package com.homeseek26.homeseeksystem.service;

import com.homeseek26.homeseeksystem.entity.Notification;
import com.homeseek26.homeseeksystem.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class NotificationService {
    
    @Autowired
    private NotificationRepository notificationRepository;
    
    public Notification createNotification(Notification notification) {
        notification.setDateSent(LocalDateTime.now());
        notification.setStatus("UNREAD");
        return notificationRepository.save(notification);
    }
    
    public Notification getNotificationById(Long id) {
        return notificationRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Notification not found with id: " + id));
    }
    
    public List<Notification> getAllNotifications() {
        return notificationRepository.findAll();
    }
    
    public Notification updateNotificationStatus(Long id, String status) {
        Notification existing = getNotificationById(id);
        existing.setStatus(status);
        return notificationRepository.save(existing);
    }
    
    public void deleteNotification(Long id) {
        notificationRepository.deleteById(id);
    }
    
    public List<Notification> getNotificationsByUser(Long userId) {
        return notificationRepository.findByUser_UserIdOrderByDateSentDesc(userId);
    }
    
    public List<Notification> getUnreadNotificationsByUser(Long userId) {
        return notificationRepository.findByUser_UserIdAndStatus(userId, "UNREAD");
    }
    
    public Notification markAsRead(Long id) {
        return updateNotificationStatus(id, "READ");
    }
    
    public int getUnreadCount(Long userId) {
        return getUnreadNotificationsByUser(userId).size();
    }
}