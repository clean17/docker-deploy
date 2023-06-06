package com.example.springbootserver.user.dto;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.example.springbootserver.user.model.User;
import lombok.*;


public class UserReq {

    @Setter
    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @EqualsAndHashCode
    public static class UserSave {

        @NotBlank(message = "아이디를 입력하세요.")
        private String username;

        @NotBlank(message = "패스워드를 입력하세요.")
        private String password;

        @NotBlank(message = "이메일을 입력하세요.")
        private String email;

        public static User toEntity(final UserReq.UserSave userSave) {
            return User.builder()
                    .email(userSave.getEmail())
                    .password(userSave.getPassword())
                    .username(userSave.getUsername())
                    .role("USER")
                    .build();
        }
    }

    @Setter
    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @EqualsAndHashCode
    public static class UserUpdate {

        @NotNull(message = "로그인이 필요한 서비스입니다.")
        private Long id;

        @NotBlank(message = "아이디를 입력하세요.")
        private String username;

        @NotBlank(message = "패스워드를 입력하세요.")
        private String password;

        @NotBlank(message = "이메일을 입력하세요.")
        private String email;

        public static User toEntity(final UserReq.UserUpdate userUpdate) {
            return User.builder()
                    .id(userUpdate.getId())
                    .email(userUpdate.getEmail())
                    .password(userUpdate.getPassword())
                    .username(userUpdate.getUsername())
                    .role("USER")
                    .build();
        }
    }
}
