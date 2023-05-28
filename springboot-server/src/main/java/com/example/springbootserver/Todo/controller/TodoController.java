package com.example.springbootserver.Todo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.springbootserver.Todo.service.TodoService;
import com.example.springbootserver.core.dto.ResponseDto;


@RestController
@RequestMapping("todo")
public class TodoController {
    
    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @GetMapping("/")
    public ResponseEntity<?> test (){
    return new ResponseEntity<>(new ResponseDto<>( "테스트", null), HttpStatus.OK);
    // return ResponseEntity.ok().body(response);
    }
}
