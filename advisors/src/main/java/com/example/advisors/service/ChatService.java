package com.example.advisors.service;

import reactor.core.publisher.Flux;

import java.util.List;

public interface ChatService {

    String getChatResponse(String query);

    Flux<String> getChatStreamResponse(String query);
}
