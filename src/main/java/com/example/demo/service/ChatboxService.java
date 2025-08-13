package com.example.demo.service;


import com.example.demo.dto.Response.ChatResponse;

import java.util.List;

public interface ChatboxService {
    ChatResponse sendMessage(com.example.demo.dto.request.ChatRequest request, String username);
    List<ChatResponse> getChatHistory(String username);
    void clearChatHistory(String username);
}