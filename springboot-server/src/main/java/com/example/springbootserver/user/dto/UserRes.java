package com.example.springbootserver.user.dto;

import com.example.springbootserver.user.model.User;
import lombok.Getter;

import java.time.LocalDateTime;


public class UserRes {

    @Getter
    public static class UserDto {
        private Long id;
        private String email;
        private String username;
        private LocalDateTime createdAt;
        private LocalDateTime updateAt;

        public UserDto(final User user) {
            this.id = user.getId();
            this.email = user.getEmail();
            this.username = user.getUsername();
            this.createdAt = user.getCreatedAt();
            this.updateAt = user.getUpdatedAt();
        }
    }
}