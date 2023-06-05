package com.example.springbootserver.user.controller;

import com.example.springbootserver.core.dto.ResponseDTO;
import com.example.springbootserver.todo.dto.TodoRes;
import com.example.springbootserver.todo.model.Todo;
import com.example.springbootserver.user.dto.UserReq;
import com.example.springbootserver.user.dto.UserRes;
import com.example.springbootserver.user.model.User;
import com.example.springbootserver.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

//    @GetMapping
//    public ResponseEntity<?> createUser() {
//        Long id = 1L;
//        List<Todo> entityList = userService.id);
//        List<TodoRes.TodoDto> todoList = new ArrayList<>();
//        for (Todo todo : entityList) {
//            TodoRes.TodoDto todoDTO = new TodoRes.TodoDto(todo);
//            todoList.add(todoDTO);
//        }
//        return new ResponseEntity<>(new ResponseDTO<>(201, "회원가입 성공", null), HttpStatus.OK);
//    }

    @PostMapping
    public ResponseEntity<?> createUser(@Valid @RequestBody UserReq.UserSave userSave) {
        User user = userService.save(userSave);
        UserRes.UserDto userDTO = null;
        if (!ObjectUtils.isEmpty(user)) {
            userDTO = new UserRes.UserDto(user);
        }
        return new ResponseEntity<>(new ResponseDTO<>(201, "User 회원가입 완료", userDTO), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<?> updateUser(@Valid @RequestBody UserReq.UserUpdate userUpdate) {
        User user = userService.update(userUpdate);
        UserRes.UserDto userDTO = null;
        if (!ObjectUtils.isEmpty(user)) {
            userDTO = new UserRes.UserDto(user);
        }
        return new ResponseEntity<>(new ResponseDTO<>(200, "User 회원수정 완료", userDTO), HttpStatus.OK);
    }
}
