package com.example.springbootserver.core.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.springbootserver.core.dto.ResponseDTO;
import com.example.springbootserver.core.exception.Exception400;
import com.example.springbootserver.core.exception.Exception401;
import com.example.springbootserver.core.exception.Exception403;
import com.example.springbootserver.core.exception.Exception404;
import com.example.springbootserver.core.exception.Exception500;

@RestControllerAdvice
public class MyExceptionAdvice {

    @ExceptionHandler(Exception400.class)
    public ResponseEntity<?> badRequest(Exception400 e) {
        ResponseDTO<?> responseDTO = new ResponseDTO<>().fail(400, e.getMessage(), null);
        return new ResponseEntity<>(responseDTO, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception401.class)
    public ResponseEntity<?> unAuthorized(Exception401 e) {
        ResponseDTO<?> responseDTO = new ResponseDTO<>().fail(401, e.getMessage(), null);
        return new ResponseEntity<>(responseDTO, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(Exception403.class)
    public ResponseEntity<?> forbidden(Exception403 e) {
        ResponseDTO<?> responseDTO = new ResponseDTO<>().fail(403, "권한이 필요합니다.", null);
        return new ResponseEntity<>(responseDTO, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(Exception404.class)
    public ResponseEntity<?> notFound(Exception404 e) {
        ResponseDTO<?> responseDTO = new ResponseDTO<>().fail(404, "리소스가 없습니다.", null);
        return new ResponseEntity<>(responseDTO, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception500.class)
    public ResponseEntity<?> serverError(Exception500 e) {
        ResponseDTO<?> responseDTO = new ResponseDTO<>().fail(500, e.getMessage(), null);
        return new ResponseEntity<>(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // 나머지 핸들링
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> serverError(Exception e) {
        ResponseDTO<?> responseDTO = new ResponseDTO<>().fail(500, e.getMessage(), null);
        return new ResponseEntity<>(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
