package com.example.springbootserver.core;


import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import com.example.springbootserver.core.auth.session.MyUserDetails;
import com.example.springbootserver.user.model.User;

import java.time.LocalDateTime;

public class MyWithMockUserFactory implements WithSecurityContextFactory<MyWithMockUser> {
    @Override
    public SecurityContext createSecurityContext(MyWithMockUser mockUser) {
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        User user = User.builder()
                .id(mockUser.id())
                .username(mockUser.username())
                .password("1234")
                .email(mockUser.username()+"@gmail.com")
                .role(mockUser.role())
                .createdAt(LocalDateTime.now())
                .build();
        MyUserDetails myUserDetails = new MyUserDetails(user);
        Authentication auth = new UsernamePasswordAuthenticationToken(myUserDetails, myUserDetails.getPassword(), myUserDetails.getAuthorities());
        securityContext.setAuthentication(auth);
        return securityContext;
    }
}