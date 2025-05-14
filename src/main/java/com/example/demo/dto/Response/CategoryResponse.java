package com.example.demo.dto.Response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryResponse {
    private Long categoryId;
    private String name;
    private String description;
    private Boolean isActive;
}
