package com.example.springbootserver.Todo.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.springbootserver.Todo.dto.TodoReq;
import com.example.springbootserver.Todo.dto.TodoRes;
import com.example.springbootserver.Todo.model.Todo;
import com.example.springbootserver.Todo.service.TodoService;
import com.example.springbootserver.core.dto.ResponseDTO;


@RestController
@RequestMapping("todos")
public class TodoController {
    
    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @GetMapping
    public ResponseEntity<?> findAll(){
        List<Todo> entityList = todoService.findAll();
        List<TodoRes.TodoDto> todoList = new ArrayList<>();
        for (Todo todo : entityList) {
            TodoRes.TodoDto todoDTO = new TodoRes.TodoDto(todo);
            todoList.add(todoDTO);
        }
    return new ResponseEntity<>(new ResponseDTO<>(200, "조회 성공", todoList), HttpStatus.OK);
    // return ResponseEntity.ok().body(response);
    }

    @PostMapping
    public ResponseEntity<?> save(@Valid @RequestBody TodoReq.TodoSave todoSave, Errors error){
        List<Todo> entityList = todoService.save(todoSave);
        List<TodoRes.TodoDto> todoList = new ArrayList<>();
        for (Todo todo : entityList) {
            TodoRes.TodoDto todoDTO = new TodoRes.TodoDto(todo);
            todoList.add(todoDTO);
        }
    return new ResponseEntity<>(new ResponseDTO<>(201, "Todo 추가 완료", todoList), HttpStatus.CREATED);
    }
}
