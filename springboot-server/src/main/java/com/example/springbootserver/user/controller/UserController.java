package com.example.springbootserver.user.controller;

import com.example.springbootserver.core.dto.ResponseDTO;
import com.example.springbootserver.user.dto.UserReq;
import com.example.springbootserver.user.dto.UserRes;
import com.example.springbootserver.user.model.User;
import com.example.springbootserver.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

   @GetMapping
   public ResponseEntity<?> findUser() {
       Long id = 1L;
       User user = userService.findUser(id);
       return new ResponseEntity<>(new ResponseDTO<>(200, "조회 성공", user), HttpStatus.OK);
   }

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
