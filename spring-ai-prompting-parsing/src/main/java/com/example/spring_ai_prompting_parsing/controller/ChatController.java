package com.example.spring_ai_prompting_parsing.controller;

import com.example.spring_ai_prompting_parsing.entity.Tutorial;
import com.example.spring_ai_prompting_parsing.service.ChatService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/")
public class ChatController {

    private ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @GetMapping("chat")
    public ResponseEntity<String> chat(@RequestParam(value="q") String query) {
        System.out.println(query);
//        String response = chatClient.prompt(query).call().content();
        String response = chatService.chat(query);
        return ResponseEntity.ok(response);
    }

    @GetMapping("chatEntity")
    public ResponseEntity<Tutorial> chatEntity(@RequestParam(value="q") String query) {
        System.out.println(query);
        Tutorial tutorial = chatService.getEntityChat(query);
        return ResponseEntity.ok(tutorial);
    }

    @GetMapping("chatEntityList")
    public ResponseEntity<List<Tutorial>> chatEntityList(@RequestParam(value="q") String query) {
        System.out.println(query);
        List<Tutorial> tutorials = chatService.getEntityChatList(query);
        return ResponseEntity.ok(tutorials);
    }
}
