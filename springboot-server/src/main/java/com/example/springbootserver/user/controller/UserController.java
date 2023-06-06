package com.example.springbootserver.user.controller;

import com.example.springbootserver.core.auth.jwt.MyJwtProvider;
import com.example.springbootserver.core.dto.ResponseDTO;
import com.example.springbootserver.user.dto.UserReq;
import com.example.springbootserver.user.dto.UserRes;
import com.example.springbootserver.user.model.User;
import com.example.springbootserver.user.service.UserService;
import com.example.springbootserver.user.service.UserService.LoginJWT;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class UserController {

    private final UserService userService;


    public UserController(UserService userService) {
        this.userService = userService;
    }

   @GetMapping("users")
   public ResponseEntity<?> findUser() {
       Long id = 1L;
       User user = userService.findUser(id);
       return new ResponseEntity<>(new ResponseDTO<>(200, "조회 성공", user), HttpStatus.OK);
   }

   @PostMapping("login")
   public ResponseEntity<?> loginUser(@Valid @RequestBody UserReq.UserLogin userLogin, Errors error){
        LoginJWT loginJWT = userService.login(userLogin);
        UserRes.UserDto userDTO = null;
        if (!ObjectUtils.isEmpty(loginJWT.getUser())) {
            userDTO = new UserRes.UserDto(loginJWT.getUser());
        }
        ResponseDTO<?> responseDTO = new ResponseDTO<>(200, "로그인 성공", userDTO);
        return ResponseEntity.ok().header(MyJwtProvider.HEADER, loginJWT.getJwt()).body(responseDTO);
   }

    @PostMapping("join")
    public ResponseEntity<?> createUser(@Valid @RequestBody UserReq.UserSave userSave, Errors error) {
        User user = userService.join(userSave);
        UserRes.UserDto userDTO = null;
        if (!ObjectUtils.isEmpty(user)) {
            userDTO = new UserRes.UserDto(user);
        }
        return new ResponseEntity<>(new ResponseDTO<>(201, "회원가입 완료", userDTO), HttpStatus.CREATED);
    }

    @PutMapping("users")
    public ResponseEntity<?> updateUser(@Valid @RequestBody UserReq.UserUpdate userUpdate, Errors error) {
        User user = userService.update(userUpdate);
        UserRes.UserDto userDTO = null;
        if (!ObjectUtils.isEmpty(user)) {
            userDTO = new UserRes.UserDto(user);
        }
        return new ResponseEntity<>(new ResponseDTO<>(200, "회원수정 완료", userDTO), HttpStatus.OK);
    }
}
