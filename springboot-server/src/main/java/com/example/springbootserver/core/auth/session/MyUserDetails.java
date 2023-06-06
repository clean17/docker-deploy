package com.example.springbootserver.core.auth.session;


import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.springbootserver.user.model.User;

import java.util.ArrayList;
import java.util.Collection;

@Setter
@Getter
public class MyUserDetails implements UserDetails {
    private User user;
//    private Date tokenDate;

    public MyUserDetails(User user) {
        this.user = user;
    }

    @Override // 권한 리스트 반환
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collector = new ArrayList<>();
        collector.add(() -> "ROLE_" + user.getRole());
        return collector;
    }

    @Override // null 가능
    public String getPassword() {
        return user.getPassword();
    }

    @Override // null 불가
    public String getUsername() {
        return user.getUsername();
    }

    @Override // 계정 만료 ( 정책에 따라 임시계정이 주어질 경우 )
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override // 계정 잠김 ( 비밀번호 연속 실패, 관리자가 밴 )
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override // 토큰 기간 만료, 비밀번호 변경기간 만료
    public boolean isCredentialsNonExpired() {
//        if(tokenDate.before(new Date())) {
//            return false;
//        } else {
//            return true;
//        }
        return true;
    }

    @Override // 계정 활성화 ( 휴면 상태 )
    public boolean isEnabled() {
        return true;
    }
}
