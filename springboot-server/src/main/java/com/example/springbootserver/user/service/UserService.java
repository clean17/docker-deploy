package com.example.springbootserver.user.service;

import com.example.springbootserver.core.auth.jwt.MyJwtProvider;
import com.example.springbootserver.core.auth.session.MyUserDetails;
import com.example.springbootserver.core.exception.*;
import com.example.springbootserver.user.dto.UserReq;
import com.example.springbootserver.user.model.User;
import com.example.springbootserver.user.model.UserRepository;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
@Slf4j
public class UserService {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder,
            AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    @Getter
    @Setter
    public static class LoginJWT {
        private User user;
        private String jwt;
    }

    @Transactional
    public User findUser(final Long id) {
        try {
            return userRepository.findById(id)
                    .orElseThrow();
        } catch (Exception e) {
            throw new Exception500("회원가입에 실패했습니다.");
        }
    }

    @Transactional
    public LoginJWT login(UserReq.UserLogin userLogin) {
        // authenticate() 메소드가 UsernamePasswordAuthenticationToken을 이용해서 인증을 구현함 ( 시큐리티
        // )
        // UserDetailsService 의 loadUserByUsername 메소드를 오버라이딩 한 설정으로 인증
        try {
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                    userLogin.getEmail(), userLogin.getPassword());
            Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
            MyUserDetails myUserDetails = (MyUserDetails) authentication.getPrincipal();
            // 위에서 익셉션 발생하지 않으면 무조건 있음
            LoginJWT loginJwt = new LoginJWT();
            loginJwt.setJwt(MyJwtProvider.create(myUserDetails.getUser()));
            loginJwt.setUser(userRepository.findById(myUserDetails.getUser().getId()).get());
            return loginJwt;
        } catch (Exception e) {
            log.error("로그인 실패 : ", userLogin.getEmail());
            throw new Exception401("로그인에 실패했습니다.");
        }
    }

    @Transactional
    public User join(final UserReq.UserSave userSave) {
        String rawPassword = userSave.getPassword();
        String encPassword = passwordEncoder.encode(rawPassword); // 60Byte
        userSave.setPassword(encPassword);

        if (userRepository.existsByEmail(userSave.getEmail())) {
            log.warn("이미 가입된 이메일입니다. : ", userSave.getEmail());
            throw new Exception400(null, "이미 가입된 이메일입니다.");
        }

        try {
            User user = UserReq.UserSave.toEntity(userSave);
            userRepository.save(user);
            log.info("User 회원가입 완료, Id : " + user.getId());
            return user;
        } catch (Exception e) {
            throw new Exception500("회원가입에 실패했습니다.");
        }
    }

    @Transactional
    public User update(final UserReq.UserUpdate userUpdate) {
        String rawPassword = userUpdate.getPassword();
        String encPassword = passwordEncoder.encode(rawPassword); // 60Byte
        userUpdate.setPassword(encPassword);

        try {
            User user = UserReq.UserUpdate.toEntity(userUpdate);
            userRepository.save(user);
            log.info("User 회원수정 완료, Id : " + user.getId());
            return user;
        } catch (Exception e) {
            throw new Exception500("회원수정에 실패했습니다.");
        }
    }

    @Transactional
    public User getByCredentials(final String email, final String password) {
        String encPassword = passwordEncoder.encode(password); // 60Byte

        return userRepository.findByEmailAndPassword(email, encPassword)
                .orElseThrow(() -> new Exception400(null, "조회 데이터가 없습니다."));
    }

}
