//package com.dgsw.org.chat.service;
//
//import com.dgsw.org.chat.dto.ChatResponse;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//import org.springframework.web.client.RestClient;
//
//import java.util.Map;
//
//@Service
//public class ChatService {
//
//    private final RestClient restClient;
//
//    public ChatService(@Value("${spring.openai.api-key}") String apiKey) {
//        System.out.println("api   key    ===  " + apiKey);
//        this.restClient = RestClient.builder()
//                .baseUrl("https://api.openai.com")
//                .defaultHeader("Authorization", "Bearer " + apiKey)
//                .defaultHeader("Content-Type", "application/json")
//                .build();
//    }
//
//    public ChatResponse chat(String message) {
//
//        Map<String, Object> request = Map.of(
//                "model", "gpt-5.6",
//                "input", message
//        );
//
//        Map response = restClient.post()
//                .uri("/v1/responses")
//                .body(request)
//                .retrieve()
//                .body(Map.class);
//
//        return new ChatResponse(
//                response.toString()
//        );
//    }
//}