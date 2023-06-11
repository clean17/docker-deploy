package com.example.springbootserver.core.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ResponseDTO<T> {
    private Integer status;
    private String msg;
    private T data;

    public ResponseDTO() {
        this.status = 200;
        this.msg = "요청 성공";
    }

    // public ResponseDTO<?> data(T data){
    //     this.data = data;
    //     return this;
    // }

    public ResponseDTO<?> fail(Integer status, String msg, T data){
        this.status = status;
        this.msg = msg;
        this.data = data;
        return this;
    }
}