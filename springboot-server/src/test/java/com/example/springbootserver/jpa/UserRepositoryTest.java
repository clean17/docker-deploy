package com.example.springbootserver.jpa;

import java.util.Optional;

import javax.transaction.Transactional;

import com.example.springbootserver.core.exception.Exception500;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import com.example.springbootserver.user.model.User;
import com.example.springbootserver.user.model.UserRepository;

@DataJpaTest
@ActiveProfiles("test")
public class UserRepositoryTest {
    
    @Autowired
    private UserRepository userRepository;

    @Test
    @Transactional
    public void findByEmail_test(){
        String email = "son@gmail.com";
        Optional<User> user = userRepository.findByEmail(email);
        user.ifPresent(e -> {
            Assertions.assertEquals(e.getId(), 1L);
            Assertions.assertEquals(e.getRole(), "USER");
        });
    }

    @Test
    @Transactional
    public void existsByEmail_test(){
        String email = "son@gmail.com";
        Boolean bool = userRepository.existsByEmail(email);
        if(!bool){
            throw new Exception500("테스트 실패");
        }
    }

    @Test
    @Transactional
    public void findByEmailAndPassword_test(){
        String email = "son@gmail.com";
        String password = "1234";
        Optional<User> user = userRepository.findByEmailAndPassword(email,password);
        user.ifPresent(e -> {
            Assertions.assertEquals(e.getId(), 1L);
            Assertions.assertEquals(e.getRole(), "USER");
        });
    }
}
