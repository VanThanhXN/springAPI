package com.example.demo.dto.Request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryRequest {
    private String name;
    private String description;
    private Boolean isActive;
}
