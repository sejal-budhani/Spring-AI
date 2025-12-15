package com.example.spring_ai_multiple.multiple_models.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.google.genai.GoogleGenAiChatModel;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChatConfig {

    @Bean(name = "genAIChatClient")
    public ChatClient genAIChatModel(GoogleGenAiChatModel genAiChatModel) {
        return ChatClient.builder(genAiChatModel).build();
    }

    @Bean(name = "ollamaChatClient")
    public ChatClient ollamaChatModel(OllamaChatModel ollamaChatModel) {
        return ChatClient.builder(ollamaChatModel).build();
    }
}
