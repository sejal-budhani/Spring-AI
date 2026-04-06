package com.example.advisors.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import org.springframework.ai.document.Document;
import java.lang.annotation.Documented;
import java.util.List;

@Service
public class ChatServiceImpl implements ChatService {

    private ChatClient chatClient;

    @Value("classpath:/prompts/system-message.st")
    private Resource systemMessage;

    @Value("classpath:/prompts/user-message.st")
    private Resource userMessage;

    private VectorStore vectorStore;

    public ChatServiceImpl(ChatClient chatClient,
                           VectorStore vectorStore) {
        this.chatClient=chatClient;
        this.vectorStore = vectorStore;
    }


    @Override
    public String getChatResponse(String query) {
//        to get the prompt through a file , we can pass it using Resource
//        String response = chatClient
//                .prompt()
//                .advisors(advisorSpec -> advisorSpec.param(ChatMemory.CONVERSATION_ID, userId))
//                .system(system -> system.text(systemMessage))
//                .user(user -> user.text(userMessage)
//                        .param("concept", query))
//                .call()
//                .content();

        SearchRequest searchRequest = SearchRequest.builder()
                .topK(5)
                .similarityThreshold(0.6)
                .query(query)
                .build();
        List<Document> documents = this.vectorStore.similaritySearch(searchRequest);
        List<String> documentList = documents.stream().map(Document::getText).toList();
        String concatenated = String.join(", ", documentList);
        return this.chatClient.prompt()
                .system(system -> system.text(systemMessage).param("documents", concatenated))
                .user(user -> user.text(userMessage)
                        .param("concept", query))
                .call()
                .content();
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

    @Override
    public void saveData(List<String> list) {
        List<Document> data = list.stream().map(Document::new).toList();
        this.vectorStore.add(data);
    }
}
