package com.example.springbootserver.Todo.dto;


import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class TodoReq {
    
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    public static class TodoSave {
        private Long userId;
        @NotBlank(message = "타이틀을 입력하세요.")
        private String title;
        @NotBlank(message = "done값을 입력하세요.")
        private boolean done;
    }

    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    public static class TodoUpdate {
        private Long id;
        private Long userId;
        @NotBlank(message = "타이틀을 입력하세요.")
        private String title;
        @NotBlank(message = "done값을 입력하세요.")
        private boolean done;
    }
}
