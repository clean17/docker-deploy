package com.example.springbootserver.core.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import com.example.springbootserver.core.dto.ResponseDTO;

// 인증 안됨
@Getter
public class Exception401 extends RuntimeException{
    public Exception401(String message) {
        super(message);
    }

    public ResponseDTO<?> body(){
        ResponseDTO<String> responseDto = new ResponseDTO<>();
        responseDto.fail(401, "unAuthorized", getMessage());
        return responseDto;
    }

    public HttpStatus status(){
        return HttpStatus.UNAUTHORIZED;
    }
}
