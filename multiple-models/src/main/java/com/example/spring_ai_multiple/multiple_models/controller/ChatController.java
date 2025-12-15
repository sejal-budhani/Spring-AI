package com.example.spring_ai_multiple.multiple_models.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.google.genai.GoogleGenAiChatModel;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/")
public class ChatController {

    private ChatClient genAIChatClient;
    private ChatClient ollamaChatClient;

    public ChatController(ChatClient genAIChatClient, ChatClient ollamaChatClient) {
        this.genAIChatClient=genAIChatClient;
        this.ollamaChatClient=ollamaChatClient;
    }

//    ==== Automatically autowiring it here, bean gets created automatically ====
//    public ChatController(GoogleGenAiChatModel genAiChatModel, OllamaChatModel ollamaChatModel) {
//        this.genAIChatClient=ChatClient.builder(genAiChatModel).build();
//        this.ollamaChatClient=ChatClient.builder(ollamaChatModel).build();
//    }

    @GetMapping("chat")
    public ResponseEntity<String> chat(@RequestParam("q") String query) {
        String response = ollamaChatClient.prompt(query).call().content();
        System.out.println(response);
        return ResponseEntity.ok(response);
    }
}
