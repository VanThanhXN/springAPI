package com.example.demo.service.impl;

import com.example.demo.Repository.*;
import com.example.demo.config.GeminiConfig;

import com.example.demo.dto.Response.ChatResponse;
import com.example.demo.entity.*;
import com.example.demo.exception.AppException;
import com.example.demo.exception.ErrorCode;
import com.example.demo.service.ChatboxService;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatboxServiceImpl implements ChatboxService {

    private static final int MAX_CONTEXT_LENGTH = 2000;
    private static final int MAX_HISTORY_LENGTH = 5;

    private final ChatboxRepository chatboxRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ReviewRepository reviewRepository;
    private final RestTemplate restTemplate;
    private final GeminiConfig geminiConfig;

    private String databaseKnowledge = "";
    private final Map<String, Queue<String>> userContexts = new ConcurrentHashMap<>();
    private LocalDateTime lastDataRefreshTime;

    @PostConstruct
    public void init() {
        refreshDatabaseKnowledge();
    }

    @Scheduled(fixedRate = 3600000) // Làm mới dữ liệu mỗi giờ
    public void refreshDatabaseKnowledge() {
        StringBuilder knowledge = new StringBuilder();

        // 1. Thông tin tổng quan hệ thống
        knowledge.append("🛍️ THÔNG TIN HỆ THỐNG BÁN HÀNG\n")
                .append("----------------------------\n")
                .append(String.format("• Tổng sản phẩm: %d\n", productRepository.count()))
                .append(String.format("• Danh mục: %d\n", categoryRepository.count()))
                .append(String.format("• Đánh giá: %d\n", reviewRepository.count()))
                .append(String.format("• Điểm trung bình: %.1f/5\n\n",
                        reviewRepository.getAverageRating() != null ?
                                reviewRepository.getAverageRating() : 0));

        // 2. Danh sách sản phẩm (ẩn ID)
        knowledge.append("📦 DANH SÁCH SẢN PHẨM\n")
                .append("-------------------\n");
        productRepository.findAll().forEach(p -> {
            knowledge.append(String.format("» %s\n", p.getName()))
                    .append(String.format("  💵 Giá: %s\n", formatPrice(p.getPrice())))
                    .append(String.format("  🔥 Giảm giá: %s\n",
                            p.getSalePrice() != null ? formatPrice(p.getSalePrice()) : "Không"))
                    .append(String.format("  ⭐ Đánh giá: %.1f/5 (%d lượt)\n",
                            p.getRating() != null ? p.getRating() : 0,
                            p.getReviewCount() != null ? p.getReviewCount() : 0))
                    .append(String.format("  📦 Tồn kho: %d\n", p.getStock() != null ? p.getStock() : 0))
                    .append(String.format("  🏷️ Danh mục: %s\n",
                            p.getCategory() != null ? p.getCategory().getName() : "Không có"))
                    .append(String.format("  🖼️ Hình ảnh: %s\n\n",
                            p.getImageUrl() != null ? "Có" : "Không"));
        });

        // 3. Thông tin danh mục
        knowledge.append("🗂️ DANH MỤC SẢN PHẨM\n")
                .append("-------------------\n");
        categoryRepository.findAll().forEach(c -> {
            knowledge.append(String.format("• %s (%d sản phẩm)\n",
                    c.getName(), productRepository.countByCategory(c)));
        });

        // 4. Đánh giá nổi bật
        knowledge.append("\n🌟 ĐÁNH GIÁ GẦN ĐÂY\n")
                .append("----------------\n");
        reviewRepository.findTop5ByOrderByCreatedAtDesc().forEach(r -> {
            knowledge.append(String.format("• %d⭐ %s\n",
                            r.getRating(), r.getProduct().getName()))
                    .append(String.format("  %s\n",
                            r.getComment() != null ?
                                    (r.getComment().length() > 50 ?
                                            r.getComment().substring(0, 50) + "..." : r.getComment())
                                    : "Không có bình luận"));
        });

        this.databaseKnowledge = knowledge.toString();
        this.lastDataRefreshTime = LocalDateTime.now();
    }

    @Override
    public ChatResponse sendMessage(com.example.demo.dto.request.ChatRequest request, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        // Lưu tin nhắn người dùng
        ChatboxMessage userMessage = saveUserMessage(user, request.getMessage());

        // Xử lý bằng AI
        String aiResponse = processUserQuery(user.getUserId().toString(), request.getMessage());

        // Lưu phản hồi AI
        ChatboxMessage aiMessage = saveAiMessage(user, aiResponse);

        return buildChatResponse(aiMessage);
    }

    private String processUserQuery(String userId, String question) {
        // 1. Kiểm tra từ khóa nhạy cảm
        if (containsSensitiveKeywords(question)) {
            return "🙅‍♂️ Xin lỗi, tôi không thể cung cấp thông tin đó.";
        }

        // 2. Xây dựng context
        String context = buildUserContext(userId, question);

        // 3. Gọi AI
        try {
            String response = callGeminiAI(buildSmartPrompt(question, context));
            updateUserContext(userId, question, response);
            return formatAiResponse(response);
        } catch (Exception e) {
            return "⚠️ Xin lỗi, đã xảy ra lỗi khi xử lý yêu cầu của bạn.";
        }
    }

    private String buildUserContext(String userId, String question) {
        Queue<String> userHistory = userContexts.getOrDefault(userId, new LinkedList<>());
        String history = String.join("\n", userHistory);

        return "🔄 Dữ liệu cập nhật lúc: " + lastDataRefreshTime + "\n\n" +
                "📊 THÔNG TIN HỆ THỐNG:\n" +
                databaseKnowledge + "\n\n" +
                "💬 LỊCH SỬ CHAT:\n" + history + "\n\n" +
                "❓ CÂU HỎI HIỆN TẠI: " + question;
    }

    private String buildSmartPrompt(String question, String context) {
        StringBuilder prompt = new StringBuilder();
        prompt.append("Bạn là trợ lý bán hàng thông minh. QUY TẮC TRẢ LỜI:\n")
                .append("1. TUYỆT ĐỐI KHÔNG hiển thị ID sản phẩm\n")
                .append("2. Format rõ ràng với emoji phù hợp\n")
                .append("3. Ưu tiên thông tin quan trọng\n")
                .append("4. Giữ phong cách thân thiện\n\n");

        // Thêm hướng dẫn cụ thể theo loại câu hỏi
        if (question.matches(".*(giá|bao nhiêu tiền|cost|price).*")) {
            prompt.append("🔢 VỀ GIÁ CẢ:\n")
                    .append("- Luôn hiển thị giá gốc và khuyến mãi (nếu có)\n")
                    .append("- Định dạng: 100,000 VND\n\n");
        }

        if (question.matches(".*(đánh giá|review|rating).*")) {
            prompt.append("⭐ VỀ ĐÁNH GIÁ:\n")
                    .append("- Tóm tắt điểm số và bình luận nổi bật\n")
                    .append("- Nếu có nhiều đánh giá, chỉ hiển thị 3 cái tiêu biểu\n\n");
        }

        prompt.append("📌 CONTEXT:\n").append(context);

        return prompt.toString();
    }

    private String callGeminiAI(String prompt) {
        String url = geminiConfig.getApiUrl() + "?key=" + geminiConfig.getApiKey();

        Map<String, Object> requestBody = new HashMap<>();
        Map<String, Object> content = new HashMap<>();
        Map<String, Object> part = new HashMap<>();

        part.put("text", prompt);
        content.put("parts", List.of(part));
        requestBody.put("contents", List.of(content));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<Map> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                entity,
                Map.class);

        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
            return extractResponseText(response.getBody());
        }
        throw new AppException(ErrorCode.AI_SERVICE_ERROR);
    }

    private void updateUserContext(String userId, String question, String response) {
        Queue<String> history = userContexts.computeIfAbsent(userId, k -> new LinkedList<>());
        history.add("Q: " + question + "\nA: " + response);

        // Giới hạn lịch sử
        while (history.size() > MAX_HISTORY_LENGTH) {
            history.poll();
        }
    }

    private String formatAiResponse(String rawResponse) {
        // Chuẩn hóa đầu ra AI
        return rawResponse.replaceAll("(?i)\\bID:\\s*\\d+\\b", "") // Loại bỏ ID
                .replaceAll("\n+", "\n") // Xóa dòng trống thừa
                .trim();
    }

    private String formatPrice(BigDecimal price) {
        return price != null ? String.format("%,d VND", price.longValue()) : "Liên hệ";
    }

    private boolean containsSensitiveKeywords(String message) {
        String[] sensitiveKeywords = {
                "password", "mật khẩu", "credit card", "thẻ tín dụng",
                "thông tin cá nhân", "personal information", "admin"
        };
        return Arrays.stream(sensitiveKeywords)
                .anyMatch(keyword -> message.toLowerCase().contains(keyword.toLowerCase()));
    }

    private String extractResponseText(Map<String, Object> response) {
        try {
            Map<String, Object> candidate = ((List<Map<String, Object>>) response.get("candidates")).get(0);
            Map<String, Object> content = (Map<String, Object>) candidate.get("content");
            List<Map<String, Object>> parts = (List<Map<String, Object>>) content.get("parts");
            return (String) parts.get(0).get("text");
        } catch (Exception e) {
            throw new AppException(ErrorCode.AI_SERVICE_ERROR);
        }
    }

    private ChatboxMessage saveUserMessage(User user, String content) {
        ChatboxMessage message = ChatboxMessage.builder()
                .user(user)
                .content(content)
                .type(MessageType.USER)
                .createdAt(LocalDateTime.now())
                .build();
        return chatboxRepository.save(message);
    }

    private ChatboxMessage saveAiMessage(User user, String content) {
        ChatboxMessage message = ChatboxMessage.builder()
                .user(user)
                .content(content)
                .type(MessageType.AI)
                .createdAt(LocalDateTime.now())
                .build();
        return chatboxRepository.save(message);
    }

    private ChatResponse buildChatResponse(ChatboxMessage message) {
        return ChatResponse.builder()
                .messageId(message.getMessageId())
                .content(message.getContent())
                .type("AI")
                .createdAt(message.getCreatedAt())
                .build();
    }

    @Override
    public List<ChatResponse> getChatHistory(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        return chatboxRepository.findByUserOrderByCreatedAtAsc(user).stream()
                .map(this::buildChatResponse)
                .collect(Collectors.toList());
    }
    @Transactional
    @Override
    public void clearChatHistory(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        chatboxRepository.deleteByUser(user);
        userContexts.remove(user.getUserId().toString());
    }
}