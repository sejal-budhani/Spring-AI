package com.example.advisors.config;

import com.example.advisors.advisors.TokenPrintAdvisor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SafeGuardAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.ollama.api.OllamaChatOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class AiConfig {

    private Logger logger = LoggerFactory.getLogger(AiConfig.class);

    @Bean
    public ChatClient chatClient(ChatClient.Builder builder) {

        return builder
                .defaultAdvisors(new SimpleLoggerAdvisor(), new SafeGuardAdvisor(List.of("game")))
//                .defaultSystem("You are a helpful coding assistant, you are an expert in coding")
//                .defaultOptions(OllamaChatOptions.builder()
//                        .temperature(0.3)
//                        .model("mxbai-embed-large")
//                        .build())
                .build();
    }
}
