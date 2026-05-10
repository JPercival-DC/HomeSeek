package com.homeseek26.homeseeksystem.repository;

import com.homeseek26.homeseeksystem.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    
    List<Notification> findByUser_UserId(Long userId);
    
    List<Notification> findByUser_UserIdAndStatus(Long userId, String status);
    
    List<Notification> findByUser_UserIdOrderByDateSentDesc(Long userId);
}