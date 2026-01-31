package com.example.advisors.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.List;

@Service
public class ChatServiceImpl implements ChatService {

    private ChatClient chatClient;

    @Value("classpath:/prompts/system-message.st")
    private Resource systemMessage;

    @Value("classpath:/prompts/user-message.st")
    private Resource userMessage;

    public ChatServiceImpl(ChatClient chatClient) {
        this.chatClient=chatClient;
    }


    @Override
    public String getChatResponse(String query) {
//        to get the prompt through a file , we can pass it using Resource
        String response = chatClient
                .prompt()
                .system(system -> system.text(systemMessage))
                .user(user -> user.text(userMessage)
                        .param("concept", query))
                .call()
                .content();
        return response;
    }

    @Override
    public Flux<String> getChatStreamResponse(String query) {
        Flux<String> streamResponse = chatClient
                .prompt()
                .system(system -> system.text(systemMessage))
                .user(user -> user.text(userMessage).param("concept", query))
                .stream()
                .content();
        return streamResponse;
    }
}
