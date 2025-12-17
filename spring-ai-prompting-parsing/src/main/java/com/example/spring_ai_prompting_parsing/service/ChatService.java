package com.example.spring_ai_prompting_parsing.service;

import com.example.spring_ai_prompting_parsing.entity.Tutorial;

import java.util.List;

public interface ChatService {

    String chat(String query);

    Tutorial getEntityChat(String query);

    List<Tutorial> getEntityChatList(String query);
}
