package com.example.demo.Repository;

import com.example.demo.entity.ChatboxMessage;
import com.example.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatboxRepository extends JpaRepository<ChatboxMessage, Long> {
    List<ChatboxMessage> findByUserOrderByCreatedAtAsc(User user);
    void deleteByUser(User user);
}