package com.example.springbootserver.user.service;

import com.example.springbootserver.core.exception.*;
import com.example.springbootserver.user.dto.UserReq;
import com.example.springbootserver.user.model.User;
import com.example.springbootserver.user.model.UserRepository;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
@Slf4j
public class UserService {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public User findUser(final Long id) {
        try {
            return userRepository.findById(id)
                .orElseThrow();
        } catch (Exception500 e) {
            throw new Exception500("회원가입에 실패했습니다.");
        }
    }

    @Transactional
    public User login(UserReq.UserLogin userLogin) {
        String rawPassword = userLogin.getPassword();
        String encPassword = passwordEncoder.encode(rawPassword); // 60Byte
        userLogin.setPassword(encPassword);

        try {
            User user = userRepository.findByEmailAndPassword(userLogin.getEmail(), userLogin.getPassword())
                .orElseThrow(() -> new Exception400(null, "이메일 혹은 비밀번호가 틀렸습니다."));
            return user;
        } catch (Exception500 e) {
            log.error("로그인 실패 : ", userLogin.getEmail());
            throw new Exception500("로그인에 실패했습니다.");
        }
    }

    @Transactional
    public User save(final UserReq.UserSave userSave) {
        String rawPassword = userSave.getPassword();
        String encPassword = passwordEncoder.encode(rawPassword); // 60Byte
        userSave.setPassword(encPassword);

        try {
            if( userRepository.existsByEmail(userSave.getEmail()) ) {
                log.warn("이미 가입된 이메일입니다. : ", userSave.getEmail());
                throw new Exception400(null, "이미 가입된 이메일입니다.");
            }
            User user = UserReq.UserSave.toEntity(userSave);
            userRepository.save(user);
            log.info("User 회원가입 완료, Id : " + user.getId());
            return user;
        } catch (Exception500 e) {
            throw new Exception500("회원가입에 실패했습니다.");
        }
    }

    @Transactional
    public User update(final UserReq.UserUpdate userUpdate) {
        String rawPassword = userUpdate.getPassword();
        String encPassword = passwordEncoder.encode(rawPassword); // 60Byte
        userUpdate.setPassword(encPassword);

        try {
            if( userRepository.existsByEmail(userUpdate.getEmail()) ) {
                log.warn("이미 가입된 이메일입니다. : ", userUpdate.getEmail());
                throw new Exception400(null, "이미 가입된 이메일입니다.");
            }
            User user = UserReq.UserUpdate.toEntity(userUpdate);
            userRepository.save(user);
            log.info("User 회원수정 완료, Id : " + user.getId());
            return user;
        } catch (Exception500 e) {
            throw new Exception500("회원수정에 실패했습니다.");
        }
    }

    @Transactional
    public User getByCredentials (final String email, final String password) {
        String encPassword = passwordEncoder.encode(password); // 60Byte
        
        try {
            return userRepository.findByEmailAndPassword(email, encPassword)
            .orElseThrow(() -> new Exception400(null,"조회 데이터가 없습니다."));
        } catch (Exception e) {
            throw new Exception500("회원 조회에 실패했습니다.");
        }
    }


}
