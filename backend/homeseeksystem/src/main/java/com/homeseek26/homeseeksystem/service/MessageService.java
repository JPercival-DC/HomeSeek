package com.homeseek26.homeseeksystem.service;

import com.homeseek26.homeseeksystem.entity.Message;
import com.homeseek26.homeseeksystem.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class MessageService {
    
    @Autowired
    private MessageRepository messageRepository;
    
    public Message sendMessage(Message message) {
        message.setTimestamp(LocalDateTime.now());
        message.setStatus("UNREAD");
        return messageRepository.save(message);
    }
    
    public Message getMessageById(Long id) {
        return messageRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Message not found with id: " + id));
    }
    
    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }
    
    public void deleteMessage(Long id) {
        messageRepository.deleteById(id);
    }
    
    public List<Message> getMessagesSentByUser(Long senderId) {
        return messageRepository.findBySender_UserId(senderId);
    }
    
    public List<Message> getMessagesReceivedByUser(Long receiverId) {
        return messageRepository.findByReceiver_UserId(receiverId);
    }
    
    public List<Message> getConversation(Long user1Id, Long user2Id) {
        return messageRepository.findConversationBetweenUsers(user1Id, user2Id);
    }
    
    public List<Message> getUnreadMessages(Long receiverId) {
        return messageRepository.findByReceiver_UserIdAndStatus(receiverId, "UNREAD");
    }
    
    public Message markAsRead(Long messageId) {
        Message message = getMessageById(messageId);
        message.setStatus("READ");
        return messageRepository.save(message);
    }
}