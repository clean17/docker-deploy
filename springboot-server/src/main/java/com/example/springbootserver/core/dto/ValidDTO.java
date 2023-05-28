package com.example.springbootserver.core.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ValidDTO {
    private String key;
    private String value;

    @Builder
    public ValidDTO(String key, String value) {
        this.key = key;
        this.value = value;
    }
}