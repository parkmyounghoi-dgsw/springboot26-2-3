//package com.dgsw.org.chat.controller;
//
//import com.dgsw.org.chat.dto.ChatRequest;
//import com.dgsw.org.chat.dto.ChatResponse;
//import com.dgsw.org.chat.service.ChatService;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/api/chat")
//public class ChatController {
//
//    private final ChatService chatService;
//
//    public ChatController(ChatService chatService) {
//        this.chatService = chatService;
//    }
//
//    @PostMapping
//    public ChatResponse chat(
//            @RequestBody ChatRequest request
//    ) {
//
//        return chatService.chat(request.message());
//    }
//}