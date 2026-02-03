package com.example.advisors.service;

import reactor.core.publisher.Flux;

public interface ChatService {

    String getChatResponse(String query, String userId);

    Flux<String> getChatStreamResponse(String query);
}
