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
        ResponseDTO<String> responseDTO = new ResponseDTO<>();
        responseDTO.fail(401, "unAuthorized", getMessage());
        return responseDTO;
    }

    public HttpStatus status(){
        return HttpStatus.UNAUTHORIZED;
    }
}
