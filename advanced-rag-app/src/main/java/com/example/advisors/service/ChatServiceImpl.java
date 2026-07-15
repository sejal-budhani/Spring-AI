package com.example.advisors.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.rag.advisor.RetrievalAugmentationAdvisor;
import org.springframework.ai.rag.generation.augmentation.ContextualQueryAugmenter;
import org.springframework.ai.rag.preretrieval.query.expansion.MultiQueryExpander;
import org.springframework.ai.rag.preretrieval.query.transformation.RewriteQueryTransformer;
import org.springframework.ai.rag.preretrieval.query.transformation.TranslationQueryTransformer;
import org.springframework.ai.rag.retrieval.join.ConcatenationDocumentJoiner;
import org.springframework.ai.rag.retrieval.search.DocumentRetriever;
import org.springframework.ai.rag.retrieval.search.VectorStoreDocumentRetriever;
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

        var advisor = RetrievalAugmentationAdvisor.builder()
                .queryTransformers(
                        RewriteQueryTransformer.builder()
                                .chatClientBuilder(chatClient.mutate().clone())
                                .build(),
                        TranslationQueryTransformer.builder()
                                .chatClientBuilder(chatClient.mutate().clone())
                                .targetLanguage("english")
                                .build()
                )
                .queryExpander(MultiQueryExpander.builder().chatClientBuilder(chatClient.mutate().clone()).build())
                .documentRetriever(VectorStoreDocumentRetriever.builder()
                        .vectorStore(vectorStore)
                        .topK(3)
                        .similarityThreshold(0.3).build())
                .documentJoiner(new ConcatenationDocumentJoiner())
                .queryAugmenter(ContextualQueryAugmenter.builder().build())
//                .documentPostProcessors()
                .build();

        return chatClient.prompt()
                .advisors(advisor)
                .user(query)
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
