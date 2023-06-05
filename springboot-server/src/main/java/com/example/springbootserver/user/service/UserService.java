package com.example.springbootserver.user.service;

import com.example.springbootserver.core.exception.*;
import com.example.springbootserver.user.dto.UserReq;
import com.example.springbootserver.user.model.User;
import com.example.springbootserver.user.model.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
@Slf4j
public class UserService {

    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public User save(final UserReq.UserSave userSave) {
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
        try {
            return userRepository.findByEmailAndPassword(email, password)
            .orElseThrow(() -> new Exception400(null,"조회 데이터가 없습니다."));
        } catch (Exception e) {
            throw new Exception500("회원 조회에 실패했습니다.");
        }
    }
}
