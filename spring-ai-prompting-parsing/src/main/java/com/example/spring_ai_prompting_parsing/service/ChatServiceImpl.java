package com.example.spring_ai_prompting_parsing.service;

import com.example.spring_ai_prompting_parsing.entity.Tutorial;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatServiceImpl implements ChatService {

    private ChatClient chatClient;

    public ChatServiceImpl(ChatClient.Builder builder) {
        this.chatClient=builder.build();
    }

    @Override
    public String chat(String query) {

//        String prompt="Tell me about Virat Kohli";

//        Chatclient's pormpt method is overloaded with 3 different parameters
//        one takes no parameters - in this one we can pass multiple types of prompts after prompt method, ex: user, system
//        second takes string as a prompt after which we can call and get content from response
//        third takes Prompt object after which we can get metadata, result and content

//        ===== One way to prmopt the AI model on the basis of system and user prompt =====
//        String response = chatClient.prompt().user(prompt).system("As an expert in cricket").call().content();

//        ===== Another way to call the prompt and get content =====
//        String response = chatClient.prompt(query).call().content();

//        ===== Another way to pass the prompt
//        Prompt prompt1 = new Prompt(prompt);
//        String response = chatClient.prompt(prompt1).call().chatResponse().getResult().getOutput().getText();

//        ===== We can get the metadata for the query prompt that we gave and the output that we got =====
//        { id: , usage: DefaultUsage{promptTokens=28, completionTokens=429, totalTokens=457}, rateLimit: org.springframework.ai.chat.metadata.EmptyRateLimit@13e8f917 }
//        var responseMetadata = chatClient.prompt(prompt1).call().chatResponse().getMetadata();
//        System.out.println(responseMetadata);


        Prompt prompt = new Prompt(query);
        String response = chatClient.prompt(prompt).call().content();

        return response;
    }

    @Override
    public Tutorial getEntityChat(String query) {
//        ===== Lets say we want to map the default string output of a query to some java class =====

        Prompt prompt = new Prompt(query);
        Tutorial tutorial = chatClient.prompt(prompt).call().entity(Tutorial.class);
        return tutorial;
    }

    @Override
    public List<Tutorial> getEntityChatList(String query) {
        Prompt prompt = new Prompt(query);
        List<Tutorial> tutorials = chatClient.prompt(prompt).call().entity(new ParameterizedTypeReference<List<Tutorial>>() {});
        return tutorials;
    }
}
