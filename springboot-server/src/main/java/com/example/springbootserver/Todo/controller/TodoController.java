package com.example.springbootserver.todo.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import com.example.springbootserver.todo.dto.TodoReq;
import com.example.springbootserver.todo.dto.TodoRes;
import com.example.springbootserver.todo.model.Todo;
import com.example.springbootserver.todo.service.TodoService;
import com.example.springbootserver.core.dto.ResponseDTO;
import com.example.springbootserver.core.exception.Exception500;


@RestController
@RequestMapping("todos")
public class TodoController {

    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @GetMapping
    public ResponseEntity<?> findbyUserId() {
        Long id = 1L;
        List<Todo> entityList = todoService.findbyUserId(id);
        List<TodoRes.TodoDto> todoList = new ArrayList<>();
        for (Todo todo : entityList) {
            TodoRes.TodoDto todoDTO = new TodoRes.TodoDto(todo);
            todoList.add(todoDTO);
        }
        return new ResponseEntity<>(new ResponseDTO<>(200, "조회 성공", todoList), HttpStatus.OK);
        // return ResponseEntity.ok().body(response);
    }

//    @GetMapping
//    public ResponseEntity<?> findAll() {
//        List<Todo> entityList = todoService.findAll();
//        List<TodoRes.TodoDto> todoList = new ArrayList<>();
//        for (Todo todo : entityList) {
//            TodoRes.TodoDto todoDTO = new TodoRes.TodoDto(todo);
//            todoList.add(todoDTO);
//        }
//        return new ResponseEntity<>(new ResponseDTO<>(200, "조회 성공", todoList), HttpStatus.OK);
//        // return ResponseEntity.ok().body(response);
//    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findOne(@PathVariable Long id) {
        Todo todoEntity = todoService.findbyId(id);
        TodoRes.TodoDto todo = new TodoRes.TodoDto(todoEntity);
        return new ResponseEntity<>(new ResponseDTO<>(200, "조회 성공", todo), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> save(@Valid @RequestBody TodoReq.TodoSave todoSave, Errors error) {
        Todo todo = todoService.save(todoSave);
        TodoRes.TodoDto todoDTO = null;
        if (!ObjectUtils.isEmpty(todo)) {
            todoDTO = new TodoRes.TodoDto(todo);
        }
        return new ResponseEntity<>(new ResponseDTO<>(201, "Todo 추가 완료", todoDTO), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<?> update(@Valid @RequestBody TodoReq.TodoUpdate todoUpdate, Errors error) {
        Todo todo = todoService.update(todoUpdate);
        TodoRes.TodoDto todoDTO = null;
        if (!ObjectUtils.isEmpty(todo)) {
            todoDTO = new TodoRes.TodoDto(todo);
        }
        return new ResponseEntity<>(new ResponseDTO<>(200, "Todo 수정 완료", todoDTO), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        todoService.delete(id);
        return new ResponseEntity<>(new ResponseDTO<>(200, "Todo 삭제 완료", null), HttpStatus.OK);
    }

}
