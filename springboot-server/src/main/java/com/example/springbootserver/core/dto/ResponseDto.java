package com.example.springbootserver.core.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ResponseDTO<T> {
    private Integer status;
    private String error;
    private T data;
    
    @Builder
    public ResponseDTO(Integer status, String error, T data) {
        this.status = status;
        this.error = error;
        this.data = data;
    }

    public ResponseDTO() {
        this.status = 200;
        this.error = "요청 성공";
    }

    public ResponseDTO<?> data(T data){
        this.data = data;
        return this;
    }

    public ResponseDTO<?> fail(Integer status, String error, T data){
        this.status = status;
        this.error = error;
        this.data = data;
        return this;
    }
}