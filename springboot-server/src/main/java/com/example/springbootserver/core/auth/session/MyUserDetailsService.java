package com.example.springbootserver.core.auth.session;

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
    // 오버라이딩하면 UsernamePasswordAuthenticationToken에 principal + credentials 정보를 넣어서 시큐리티의 인증 매커니즘을 이용할 수 있게 된다.
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
        // User userPS = userRepository.findByUsername(username)
                .orElseThrow(
                        () -> new UsernameNotFoundException("인증에 실패했습니다."));
        return new MyUserDetails(user);
    }
}