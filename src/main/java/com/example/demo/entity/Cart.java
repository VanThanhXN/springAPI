    package com.example.demo.entity;

    import jakarta.persistence.*;
    import lombok.*;
    import java.time.LocalDateTime;

    @Entity
    @Table(name = "cart")
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public class Cart {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "cart_id")
        private Long cartId;

        @ManyToOne
        @JoinColumn(name = "user_id", nullable = false)
        private User user;

        @ManyToOne
        @JoinColumn(name = "product_id", nullable = false)
        private Product product;

        private Integer quantity;

        @Column(name = "added_at")
        private LocalDateTime addedAt;
    }