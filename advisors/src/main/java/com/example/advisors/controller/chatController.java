package com.example.advisors.controller;


import com.example.advisors.service.ChatService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class chatController {

    private ChatService chatService;

    public chatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @GetMapping("/chat")
    public ResponseEntity<String> chatResponse(@RequestParam(value = "q") String query) {
        System.out.println(query);
        return ResponseEntity.ok(chatService.getChatResponse(query));
    }
}
