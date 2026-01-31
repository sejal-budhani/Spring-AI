package com.example.advisors.config;

import com.example.advisors.advisors.TokenPrintAdvisor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SafeGuardAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class AiConfig {

    @Bean
    public ChatClient chatClient(ChatClient.Builder builder) {
        return builder
//                .defaultAdvisors(new TokenPrintAdvisor(), new SimpleLoggerAdvisor(), new SafeGuardAdvisor(List.of("game")))
                .defaultAdvisors(new TokenPrintAdvisor(), new SafeGuardAdvisor(List.of("game")))
                .defaultSystem("You are a helpful coding assistant, you are an expert in coding")
                .build();
    }
}
