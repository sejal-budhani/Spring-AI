package com.example.spring_ai_ollama.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/")
public class ChatController {

    private ChatClient chatClient;

    public ChatController(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }

    @GetMapping("chat")
    public ResponseEntity<String> chat(@RequestParam(value="q") String query) {
        System.out.println(query);
        String response = chatClient.prompt(query).call().content();
        return ResponseEntity.ok(response);
    }
}
