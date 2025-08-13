package com.example.demo.controller;


import com.example.demo.dto.Response.ApiResponse;
import com.example.demo.dto.Response.ChatResponse;
import com.example.demo.service.ChatboxService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
public class ChatboxController {

    private final ChatboxService chatboxService;

    @PostMapping("/send")
    public ApiResponse<ChatResponse> sendMessage(@RequestBody com.example.demo.dto.request.ChatRequest request) {
        String username = getCurrentUsername();
        ChatResponse response = chatboxService.sendMessage(request, username);
        return ApiResponse.<ChatResponse>builder()
                .result(response)
                .build();
    }

    @GetMapping("/history")
    public ApiResponse<List<ChatResponse>> getChatHistory() {
        String username = getCurrentUsername();
        List<ChatResponse> history = chatboxService.getChatHistory(username);
        return ApiResponse.<List<ChatResponse>>builder()
                .result(history)
                .build();
    }

    @DeleteMapping("/clear")
    public ApiResponse<String> clearChatHistory() {
        String username = getCurrentUsername();
        chatboxService.clearChatHistory(username);
        return ApiResponse.<String>builder()
                .result("Lịch sử chat đã được xóa")
                .build();
    }

    private String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }
}