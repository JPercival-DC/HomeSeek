package com.homeseek26.homeseeksystem.repository;

import com.homeseek26.homeseeksystem.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    
    List<Message> findBySender_UserId(Long senderId);
    
    List<Message> findByReceiver_UserId(Long receiverId);
    
    @Query("SELECT m FROM Message m WHERE (m.sender.userId = :user1Id AND m.receiver.userId = :user2Id) OR (m.sender.userId = :user2Id AND m.receiver.userId = :user1Id) ORDER BY m.timestamp")
    List<Message> findConversationBetweenUsers(@Param("user1Id") Long user1Id, @Param("user2Id") Long user2Id);
    
    List<Message> findByReceiver_UserIdAndStatus(Long receiverId, String status);
}