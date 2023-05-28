package com.example.springbootserver.core.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import com.example.springbootserver.core.dto.ResponseDTO;
import com.example.springbootserver.core.dto.ValidDTO;

@Getter
public class Exception400 extends RuntimeException {

    private String key;
    private String value;

    public Exception400(String key, String value) {
        super(value);
        this.key = key;
        this.value = value;
    }

    public ResponseDTO<?> body(){
        ValidDTO validDTO = new ValidDTO(key, value);
        return new ResponseDTO<>(400, "BadRequest", validDTO);
    }

    public HttpStatus status(){
        return HttpStatus.BAD_REQUEST;
    }
}
