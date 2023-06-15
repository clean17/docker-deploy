package com.example.springbootserver.todos.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import com.example.springbootserver.todos.dto.TodoReq;
import com.example.springbootserver.todos.dto.TodoRes;
import com.example.springbootserver.todos.model.Todo;
import com.example.springbootserver.todos.service.TodoService;
import com.example.springbootserver.core.auth.session.MyUserDetails;
import com.example.springbootserver.core.dto.ResponseDTO;


@RestController
@RequestMapping("todos")
public class TodoController {
    
    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @GetMapping
    public ResponseEntity<?> findByUserId(@AuthenticationPrincipal MyUserDetails userDetails) {
        List<Todo> entityList = todoService.findByUserId(userDetails);
        List<TodoRes.TodoDto> todoList = new ArrayList<>();
        for (Todo todo : entityList) {
            TodoRes.TodoDto todoDTO = new TodoRes.TodoDto(todo);
            todoList.add(todoDTO);
        }
        return new ResponseEntity<>(new ResponseDTO<>(200, "조회 성공", todoList), HttpStatus.OK);
        // return ResponseEntity.ok().body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findOne(@AuthenticationPrincipal MyUserDetails userDetails, @PathVariable Long id) {
        Todo todoEntity = todoService.findbyId(id, userDetails);
        TodoRes.TodoDto todo = new TodoRes.TodoDto(todoEntity);
        return new ResponseEntity<>(new ResponseDTO<>(200, "조회 성공", todo), HttpStatus.OK);
    }

    // AuthenticationPrincipal은 시큐리티에서 제공하는 기능으로 인증된 사용자의 세부정보를 가져온다.
    // 시큐리티 컨텍스트에 있는 Authentication객체에서 세부정보를 가져온다. - serDetails.getUsername() 이렇게 꺼내면 된다.
    @PostMapping
    public ResponseEntity<?> save(@AuthenticationPrincipal MyUserDetails userDetails, @Valid @RequestBody TodoReq.TodoSave todoSave, Errors error) {
        Todo todo = todoService.save(todoSave, userDetails);
        TodoRes.TodoDto todoDTO = null;
        if (!ObjectUtils.isEmpty(todo)) {
            todoDTO = new TodoRes.TodoDto(todo);
        }
        return new ResponseEntity<>(new ResponseDTO<>(201, "Todo 추가 완료", todoDTO), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<?> update(@AuthenticationPrincipal MyUserDetails userDetails, @Valid @RequestBody TodoReq.TodoUpdate todoUpdate, Errors error) {
        Todo todo = todoService.update(todoUpdate, userDetails);
        TodoRes.TodoDto todoDTO = null;
        if (!ObjectUtils.isEmpty(todo)) {
            todoDTO = new TodoRes.TodoDto(todo);
        }
        return new ResponseEntity<>(new ResponseDTO<>(200, "Todo 수정 완료", todoDTO), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@AuthenticationPrincipal MyUserDetails userDetails, @PathVariable Long id){
        todoService.delete(id, userDetails);
        return new ResponseEntity<>(new ResponseDTO<>(200, "Todo 삭제 완료", null), HttpStatus.OK);
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
}
