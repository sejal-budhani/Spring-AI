package com.example.helpdesk.Spring_AI_Helpdesk.service;

import com.example.helpdesk.Spring_AI_Helpdesk.tools.TicketDatabaseTools;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Getter
@Setter
public class AiService {

    @Value("classpath:helpdesk_system.st")
    private Resource systemPrompt;

    private final ChatClient chatClient;
    private final TicketDatabaseTools ticketDatabaseTools;

    public String getResponseFromAssistant(String query, String conversationId) {
        return this.chatClient
                .prompt()
                .advisors(advisorSpec -> advisorSpec.param(ChatMemory.CONVERSATION_ID, conversationId))
                .tools(ticketDatabaseTools)
                .user(query)
                .system(systemPrompt)
                .call()
                .content();
    }
}
