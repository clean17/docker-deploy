package com.example.springbootserver.todo.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.example.springbootserver.todo.model.Todo;

import lombok.*;

import java.util.Objects;

public class TodoReq {

    @Setter
    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @EqualsAndHashCode
    public static class TodoSave {
        private Long userId;
        @NotBlank(message = "타이틀을 입력하세요.")
        private String title;
//        @NotNull(message = "done값을 입력하세요.") // boolean 은 NotBlank 불가 NotBlank 는 String 받을때
        private boolean done;

        public static Todo toEntity(final TodoReq.TodoSave todoSave) {
            return Todo.builder()
                    .userId(todoSave.getUserId())
                    .title(todoSave.getTitle())
                    .done(false)
                    .build();
        }
    }

    @Setter
    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @EqualsAndHashCode
    public static class TodoUpdate {
        @NotNull(message = "id값이 필요합니다.") // Long 타입도 NotBlack 불가
        private Long id;
        private Long userId;
        @NotBlank(message = "타이틀을 입력하세요.")
        private String title;
        @NotNull(message = "done값을 입력하세요.")
        private boolean done;

        public static Todo toEntity(final TodoUpdate todoUpdate) {
            return Todo.builder()
                    .id(todoUpdate.getId())
                    .userId(todoUpdate.getUserId())
                    .title(todoUpdate.getTitle())
                    .done(todoUpdate.isDone())
                    .build();
        }
    }
}
