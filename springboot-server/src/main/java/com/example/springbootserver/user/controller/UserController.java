package com.example.springbootserver.user.controller;

import com.example.springbootserver.core.auth.jwt.MyJwtProvider;
import com.example.springbootserver.core.dto.ResponseDTO;
import com.example.springbootserver.user.dto.UserReq;
import com.example.springbootserver.user.dto.UserRes;
import com.example.springbootserver.user.model.User;
import com.example.springbootserver.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.method.P;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class UserController {

    private final UserService userService;

    private final BCryptPasswordEncoder passwordEncoder;

    public UserController(UserService userService, BCryptPasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

   @GetMapping("users")
   public ResponseEntity<?> findUser() {
       Long id = 1L;
       User user = userService.findUser(id);
       return new ResponseEntity<>(new ResponseDTO<>(200, "조회 성공", user), HttpStatus.OK);
   }

   @PostMapping("login")
   public ResponseEntity<?> loginUser(@Valid @RequestBody UserReq.UserLogin userLogin, Errors error){
        User user = userService.login(userLogin);
        UserRes.UserDto userDTO = null;
        if (!ObjectUtils.isEmpty(user)) {
            userDTO = new UserRes.UserDto(user);
        }
        String jwt = "";
        // matches() 는 Salt를 고려해 비교해준다.
        if (passwordEncoder.matches(userLogin.getPassword(), user.getPassword())) {
            jwt = MyJwtProvider.create(user);
        }
        ResponseDTO<?> responseDTO = new ResponseDTO<>(200, "로그인 성공", userDTO);
        return ResponseEntity.ok().header(MyJwtProvider.HEADER, jwt).body(responseDTO);
   }

    @PostMapping("join")
    public ResponseEntity<?> createUser(@Valid @RequestBody UserReq.UserSave userSave, Errors error) {
        User user = userService.save(userSave);
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
