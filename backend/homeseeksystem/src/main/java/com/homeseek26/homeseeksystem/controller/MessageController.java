package com.homeseek26.homeseeksystem.controller;

import com.homeseek26.homeseeksystem.entity.Message;
import com.homeseek26.homeseeksystem.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/messages")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:5173"})
public class MessageController {
    
    @Autowired
    private MessageService messageService;
    
    @PostMapping
    public Message sendMessage(@RequestBody Message message) {
        return messageService.sendMessage(message);
    }
    
    @GetMapping("/{id}")
    public Message getMessageById(@PathVariable Long id) {
        return messageService.getMessageById(id);
    }
    
    @GetMapping
    public List<Message> getAllMessages() {
        return messageService.getAllMessages();
    }
    
    @DeleteMapping("/{id}")
    public String deleteMessage(@PathVariable Long id) {
        messageService.deleteMessage(id);
        return "Message deleted successfully";
    }
    
    @GetMapping("/sent/{senderId}")
    public List<Message> getSentMessages(@PathVariable Long senderId) {
        return messageService.getMessagesSentByUser(senderId);
    }
    
    @GetMapping("/received/{receiverId}")
    public List<Message> getReceivedMessages(@PathVariable Long receiverId) {
        return messageService.getMessagesReceivedByUser(receiverId);
    }
    
    @GetMapping("/conversation")
    public List<Message> getConversation(@RequestParam Long user1Id, @RequestParam Long user2Id) {
        return messageService.getConversation(user1Id, user2Id);
    }
    
    @GetMapping("/unread/{receiverId}")
    public List<Message> getUnreadMessages(@PathVariable Long receiverId) {
        return messageService.getUnreadMessages(receiverId);
    }
    
    @PutMapping("/{id}/read")
    public Message markAsRead(@PathVariable Long id) {
        return messageService.markAsRead(id);
    }
}