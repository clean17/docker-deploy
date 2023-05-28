package com.example.springbootserver.core.auth.session;

import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.springbootserver.user.model.User;
import com.example.springbootserver.user.model.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MyUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    // /login + Post + FormUrlEncoded 로 인증되면 UserDetails 리턴
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User userPS = userRepository.findByUsername(username)
                .orElseThrow(
                        () -> new InternalAuthenticationServiceException("인증 실패"));
        return new MyUserDetails(userPS);
    }
}
