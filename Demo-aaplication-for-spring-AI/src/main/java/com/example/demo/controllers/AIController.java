package com.example.demo.controllers;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class AIController {

    private ChatClient chatClient;

    public AIController(ChatClient.Builder builder){
        this.chatClient = builder.build();
    }

    @GetMapping("chat")
    public ResponseEntity<String> chat(
            @RequestParam(value = "q", required = true) String q
    ) {
        String response = chatClient.prompt(q).call().content();
        System.out.println(response);
        return ResponseEntity.ok(response);
    }
}
