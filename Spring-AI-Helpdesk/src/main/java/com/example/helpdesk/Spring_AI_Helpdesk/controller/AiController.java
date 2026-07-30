package com.example.helpdesk.Spring_AI_Helpdesk.controller;

import com.example.helpdesk.Spring_AI_Helpdesk.service.AiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v1/ai")
public class AiController {

    private final AiService aiService;

    @PostMapping("/response")
    public ResponseEntity<String> getResponse(@RequestBody String query) {
        return ResponseEntity.ok(this.aiService.getResponseFromAssistant(query));
    }
}
